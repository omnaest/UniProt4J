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
import java.util.Collections;
import java.util.stream.Stream;

import org.omnaest.uniprot.UniProtRESTUtils.UniProtRawRESTAPIAccessor;
import org.omnaest.uniprot.domain.rest.Entry;
import org.omnaest.uniprot.domain.rest.GetEntityResponse;
import org.omnaest.uniprot.domain.rest.SearchResponse;
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

		public Stream<EntityAccessor> searchFor(String query);

		public EntityAccessor getByUniProtId(String id);

	}

	public static interface EntityAccessor
	{
		public Entry get();
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
					public Stream<EntityAccessor> searchFor(String query)
					{
						SearchResponse searchResponse = this.getRestAPIAccessorInstance()
															.searchFor(query);

						return (searchResponse == null ? Collections.<Entry>emptyList() : searchResponse.getEntries())	.stream()
																														.map(entry -> this.createEntityAccessor(entry));
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

				};
			}
		};
	}
}
