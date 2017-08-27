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
import java.util.stream.Stream;

import org.junit.Test;
import org.omnaest.uniprot.UniProtUtils.EntityAccessor;
import org.omnaest.uniprot.UniProtUtils.UniProtRESTAccessor;
import org.omnaest.utils.JSONHelper;

public class UniProtUtilsTest
{

	@Test
	public void testGetInstance() throws Exception
	{
		UniProtRESTAccessor uniProtRESTAccessor = UniProtUtils	.getInstance()
																.useRESTApi()
																//.withSingleTempFileCache();
																.withSingleFileCache(new File("C:/Temp/uniprot_cache.json"));

		//		String json = JSONHelper.prettyPrint(uniProtRESTAccessor.searchFor("ADH")
		//																.limit(1)
		//																.map(entity -> entity.get())
		//																.collect(Collectors.toList()));

		uniProtRESTAccessor	.searchFor(t -> t.limit(20), "ACOT organism:\"homo sapiens\"")
							.forEach(accessor ->
							{
								System.out.println(accessor	.get()
															.getName());
								System.out.println(JSONHelper.prettyPrint(accessor.getMetalBindings()));
								System.out.println(JSONHelper.prettyPrint(accessor.getBindings()));
							});

		Stream<EntityAccessor> accessors = uniProtRESTAccessor.getByUniProtId("Q00955", "Q8WYK0");
		//				Arrays	.asList("ADH", "ACSS")
		//													.stream()
		//													.flatMap(query -> uniProtRESTAccessor	.searchFor(query)
		//																							.limit(1));
		//		accessors.forEach(entityAccessor ->
		//		{
		//			List<Binding> bindings = entityAccessor.getBindings();
		//			System.out.println(JSONHelper.prettyPrint(bindings));
		//		});

		//System.out.println(JSONHelper.prettyPrint(codes));

	}

}
