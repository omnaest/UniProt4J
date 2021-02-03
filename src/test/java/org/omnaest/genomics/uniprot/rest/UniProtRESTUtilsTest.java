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
package org.omnaest.genomics.uniprot.rest;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.genomics.uniprot.domain.raw.SearchResponse;
import org.omnaest.genomics.uniprot.rest.UniProtRESTUtils;
import org.omnaest.utils.JSONHelper;

public class UniProtRESTUtilsTest
{

    @Test
    @Ignore
    public void testSearchFor() throws Exception
    {
        SearchResponse searchResponse = UniProtRESTUtils.getInstance()
                                                        .searchFor("ADH", 0, 1);

        System.out.println(JSONHelper.prettyPrint(searchResponse));
    }

}