/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.uniprot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.omnaest.uniprot.UniProtRESTUtils.UniProtRawRESTAPIAccessor;
import org.omnaest.uniprot.domain.Binding;
import org.omnaest.uniprot.domain.CodeAndPosition;
import org.omnaest.uniprot.domain.MetalBinding;
import org.omnaest.uniprot.domain.raw.Entry;
import org.omnaest.uniprot.domain.raw.Feature;
import org.omnaest.uniprot.domain.raw.Feature.Type;
import org.omnaest.uniprot.domain.raw.GetEntityResponse;
import org.omnaest.uniprot.domain.raw.Location;
import org.omnaest.uniprot.domain.raw.Position;
import org.omnaest.uniprot.domain.raw.SearchResponse;
import org.omnaest.uniprot.domain.raw.Sequence;
import org.omnaest.utils.StreamUtils;
import org.omnaest.utils.cache.Cache;
import org.omnaest.utils.cache.CacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniProtUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(UniProtUtils.class);

	public static interface UniProtLoader
	{
		public UniProtRESTAccessor useRESTApi();
	}

	public static interface UniProtRESTAccessor
	{
		/**
		 * @see Cache
		 * @param cache
		 * @return
		 */
		public UniProtRESTAccessor withCache(Cache cache);

		/**
		 * @see #withCache(Cache)
		 * @param file
		 * @return
		 */
		public UniProtRESTAccessor withSingleFileCache(File file);

		/**
		 * @see #withSingleFileCache(File)
		 * @see File#createTempFile(String, String)
		 * @return
		 */
		public UniProtRESTAccessor withSingleTempFileCache();

		public Stream<EntityAccessor> searchFor(String... querys);

		public Stream<EntityAccessor> searchFor(UnaryOperator<Stream<EntityAccessor>> partialSearchModifier, String... querys);

		public Stream<EntityAccessor> searchFor(String query);

		public Stream<EntityAccessor> getByUniProtId(String... ids);

		public EntityAccessor getByUniProtId(String id);

		UniProtRESTAccessor withDirectoryCache(File directory);

		UniProtRESTAccessor withTempDirectoryCache();

	}

	public static interface EntityAccessor
	{
		public Entry get();

		public List<MetalBinding> getMetalBindings();

		public List<Binding> getBindings();

		public SequenceAccessor getSequence();
	}

	public static interface SequenceAccessor
	{
		boolean hasSequence();

		public String get();

		public Stream<CodeAndPosition> getAsStream();
	}

	public static UniProtLoader getInstance()
	{
		return new UniProtLoader()
		{

			@Override
			public UniProtRESTAccessor useRESTApi()
			{
				return new UniProtRESTAccessor()
				{
					private Cache cache = CacheUtils.newConcurrentInMemoryCache();

					@Override
					public UniProtRESTAccessor withCache(Cache cache)
					{
						this.cache = cache;
						return this;
					}

					@Override
					public UniProtRESTAccessor withSingleFileCache(File file)
					{
						return this.withCache(CacheUtils.newJsonFileCache(file));
					}

					@Override
					public UniProtRESTAccessor withSingleTempFileCache()
					{
						try
						{
							this.withSingleFileCache(File.createTempFile("uniprotCache", "json"));
						}
						catch (IOException e)
						{
							LOG.error("Failed to create temp file cache", e);
						}
						return this;
					}

					@Override
					public UniProtRESTAccessor withTempDirectoryCache()
					{
						try
						{
							this.withDirectoryCache(Files	.createTempDirectory("uniprotCache")
															.toFile());
						}
						catch (IOException e)
						{
							LOG.error("Failed to create temp cache directory", e);
						}
						return this;
					}

					@Override
					public UniProtRESTAccessor withDirectoryCache(File directory)
					{
						return this.withCache(CacheUtils.newJsonFolderCache(directory));
					}

					@Override
					public Stream<EntityAccessor> searchFor(String query)
					{
						return StreamUtils	.fromSupplier(new Supplier<List<EntityAccessor>>()
						{
							private int	limit	= 25;
							private int	offset	= 0;

							@Override
							public List<EntityAccessor> get()
							{
								return searchFor(query, this.getAndIncrementOffset(), this.limit);
							}

							private int getAndIncrementOffset()
							{
								int retval = this.offset;
								this.offset += this.limit;
								return retval;
							}
						}, (result) -> result == null || result.isEmpty())
											.flatMap(batch -> batch.stream());
					}

					@Override
					public Stream<EntityAccessor> searchFor(String... querys)
					{
						return this.searchFor(s -> s, querys);
					}

					@Override
					public Stream<EntityAccessor> searchFor(UnaryOperator<Stream<EntityAccessor>> partialSearchModifier, String... querys)
					{
						return StreamUtils.concat(Arrays.asList(querys)
														.stream()
														.map(this::searchFor)
														.map(accessors -> partialSearchModifier.apply(accessors)));
					}

					public List<EntityAccessor> searchFor(String query, int offset, int limit)
					{
						SearchResponse searchResponse = this.getRestAPIAccessorInstance()
															.searchFor(query, offset, limit);

						return (searchResponse == null ? Collections.<Entry>emptyList() : searchResponse.getEntries())	.stream()
																														.map(entry -> this.createEntityAccessor(entry))
																														.collect(Collectors.toList());
					}

					private UniProtRawRESTAPIAccessor getRestAPIAccessorInstance()
					{
						return UniProtRESTUtils.getInstance(this.cache);
					}

					private EntityAccessor createEntityAccessor(Entry entry)
					{
						return new EntityAccessor()
						{
							@Override
							public Entry get()
							{
								return entry;
							}

							@Override
							public List<MetalBinding> getMetalBindings()
							{
								return this	.get()
											.getFeatures()
											.stream()
											.filter(feature -> !feature.isMarkedAsRemoved())
											.filter(feature -> feature.isOfType(Type.METAL_BINDING_SITE))
											.map(feature ->
											{
												int positionValue = this.determinePositionValue(feature);
												String metalAndType = feature.getDescription();
												String[] tokens = StringUtils.split(metalAndType, ";");
												String metal = tokens.length >= 1 ? tokens[0].trim() : "";
												String type = tokens.length >= 2 ? tokens[1].trim() : "";
												return new MetalBinding(metal, type, Arrays	.asList(positionValue)
																							.stream()
																							.filter(value -> value >= 0)
																							.collect(Collectors.toList()));
											})
											.collect(Collectors.groupingBy(metalBinding -> Arrays.asList(metalBinding.getMetal(), metalBinding.getType())))
											.entrySet()
											.stream()
											.map(entry -> new MetalBinding(	entry	.getKey()
																					.get(0),
																			entry	.getKey()
																					.get(1),
																			entry	.getValue()
																					.stream()
																					.flatMap(binding -> binding	.getPositions()
																												.stream())
																					.collect(Collectors.toSet())))
											.collect(Collectors.toList());
							}

							@Override
							public SequenceAccessor getSequence()
							{
								Sequence sequence = this.get()
														.getSequence();
								return new SequenceAccessor()
								{

									@Override
									public Stream<CodeAndPosition> getAsStream()
									{
										int length = NumberUtils.toInt(sequence.getLength(), -1);

										AtomicInteger position = new AtomicInteger();
										Pattern codePattern = Pattern.compile("[a-zA-Z0-9]");
										List<CodeAndPosition> retval = Arrays	.asList(ArrayUtils.toObject(this.get()
																												.toCharArray()))
																				.stream()
																				.filter(code -> codePattern	.matcher(String.valueOf(code))
																											.matches())
																				.map(code -> new CodeAndPosition(code, position.getAndIncrement()))
																				.collect(Collectors.toList());

										if (position.get() != length)
										{
											throw new IllegalStateException("Sequence length differs to actual sequence code: " + length + " != "
													+ position.get());
										}

										return retval.stream();
									}

									@Override
									public String get()
									{
										return sequence.getValue();
									}

									@Override
									public boolean hasSequence()
									{
										return StringUtils.isNotBlank(this.get());
									}

								};
							}

							@Override
							public List<Binding> getBindings()
							{
								return this	.get()
											.getFeatures()
											.stream()
											.filter(feature -> !feature.isMarkedAsRemoved())
											.filter(feature -> feature.isOfType(Type.BINDING_SITE))
											.map(feature ->
											{
												int positionValue = this.determinePositionValue(feature);
												String compoundAndDescription = feature.getDescription();
												String[] tokens = StringUtils.split(compoundAndDescription, ";");
												String compound = tokens.length >= 1 ? tokens[0].trim() : "";
												String description = tokens.length >= 2 ? tokens[1].trim() : "";
												return new Binding(compound, description, Arrays.asList(positionValue)
																								.stream()
																								.filter(value -> value >= 0)
																								.collect(Collectors.toList()));
											})
											.collect(Collectors.groupingBy(metalBinding -> Arrays.asList(	metalBinding.getCompound(),
																											metalBinding.getDescription())))
											.entrySet()
											.stream()
											.map(entry -> new Binding(	entry	.getKey()
																				.get(0),
																		entry	.getKey()
																				.get(1),
																		entry	.getValue()
																				.stream()
																				.flatMap(binding -> binding	.getPositions()
																											.stream())
																				.collect(Collectors.toSet())))
											.collect(Collectors.toList());
							}

							private int determinePositionValue(Feature feature)
							{
								return NumberUtils.toInt(((Supplier<String>) () ->
								{
									Location location = feature.getLocation();
									Position position = location != null ? location.getPosition() : null;
									return position != null ? position.getPosition() : null;
								}).get(), -1);
							}

						};
					}

					@Override
					public EntityAccessor getByUniProtId(String id)
					{
						GetEntityResponse response = this	.getRestAPIAccessorInstance()
															.getEntity(id);
						return response == null ? null : this.createEntityAccessor(response	.getEntries()
																							.stream()
																							.findFirst()
																							.get());
					}

					@Override
					public Stream<EntityAccessor> getByUniProtId(String... ids)
					{
						return StreamUtils.concat(Arrays.asList(ids)
														.stream()
														.map(id -> Stream.of(this.getByUniProtId(id))));
					}

				};
			}
		};
	}
}
