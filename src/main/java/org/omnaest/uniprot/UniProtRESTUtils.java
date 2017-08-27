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

import org.omnaest.uniprot.domain.rest.GetEntityResponse;
import org.omnaest.uniprot.domain.rest.SearchResponse;
import org.omnaest.utils.cache.Cache;
import org.omnaest.utils.rest.client.CachedRestClient;
import org.omnaest.utils.rest.client.RestClient;
import org.omnaest.utils.rest.client.XMLRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniProtRESTUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(UniProtRESTUtils.class);

	public static interface UniProtRawRESTAPIAccessor
	{
		public SearchResponse searchFor(String query, int offset, int limit);

		public GetEntityResponse getEntity(String entityId);
	}

	public static UniProtRawRESTAPIAccessor getInstance()
	{
		Cache cache = null;
		return getInstance(cache);
	}

	public static UniProtRawRESTAPIAccessor getInstance(Cache cache)
	{
		return new UniProtRawRESTAPIAccessor()
		{
			private RestClient restClient = new CachedRestClient(new XMLRestClient()).setCache(cache);

			@Override
			public SearchResponse searchFor(String query, int offset, int limit)
			{
				String url = this.restClient.urlBuilder()
											.setBaseUrl("http://www.uniprot.org/uniprot")
											.addQueryParameter("query", query)
											.addQueryParameter("offset", "" + offset)
											.addQueryParameter("limit", "" + limit)
											.addQueryParameter("sort", "score")
											.addQueryParameter("format", "xml")
											.build();

				LOG.info("Request url: " + url);
				return this.restClient.requestGet(url, SearchResponse.class);
			}

			@Override
			public GetEntityResponse getEntity(String entityId)
			{
				String url = "http://www.uniprot.org/uniprot/" + entityId + ".xml";
				LOG.info("Request url: " + url);
				return this.restClient.requestGet(url, GetEntityResponse.class);
			}
		};
	}

}
