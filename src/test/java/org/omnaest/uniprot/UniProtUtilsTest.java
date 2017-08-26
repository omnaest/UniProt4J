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
import java.util.stream.Collectors;

import org.junit.Test;
import org.omnaest.uniprot.UniProtUtils.UniProtRESTAccessor;
import org.omnaest.utils.JSONHelper;

public class UniProtUtilsTest
{

	@Test
	public void testGetInstance() throws Exception
	{
		UniProtRESTAccessor uniProtRESTAccessor = UniProtUtils	.getInstance()
																.useRESTApi()
																.withSingleFileCache(new File("C:/Temp/uniprot_cache.json"));
		{
			String json = JSONHelper.prettyPrint(uniProtRESTAccessor.searchFor("ACSS1")
																	.map(entity -> entity.get())
																	.collect(Collectors.toList()));

			System.out.println(json);
		}

		System.out.println("again ----------------------------------------------------------------");

		{
			String json = JSONHelper.prettyPrint(uniProtRESTAccessor.searchFor("ACSS1")
																	.map(entity -> entity.get())
																	.collect(Collectors.toList()));

			System.out.println(json);
		}
	}

}
