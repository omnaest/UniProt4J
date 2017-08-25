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

import org.omnaest.uniprot.domain.rest.SearchResponse;
import org.omnaest.utils.rest.client.RestClient;
import org.omnaest.utils.rest.client.XMLRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniProtRESTUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(UniProtRESTUtils.class);

	public static SearchResponse searchFor(String query)
	{
		RestClient restClient = new XMLRestClient();
		String url = "http://www.uniprot.org/uniprot/?query=" + query + "&sort=score&format=xml";
		LOG.info("Request url: " + url);
		return restClient.requestGet(url, SearchResponse.class);
	}
}
