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
package org.omnaest.genomics.uniprot;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.genomics.uniprot.UniProtUtils.EntityAccessor;
import org.omnaest.genomics.uniprot.UniProtUtils.UniProtRESTAccessor;
import org.omnaest.genomics.uniprot.domain.ActiveSite;
import org.omnaest.genomics.uniprot.domain.Binding;
import org.omnaest.genomics.uniprot.domain.ModifiedResidue;
import org.omnaest.genomics.uniprot.domain.raw.Feature;
import org.omnaest.utils.JSONHelper;

public class UniProtUtilsTest
{

    @Test
    @Ignore
    public void testGetInstance() throws Exception
    {
        UniProtRESTAccessor uniProtRESTAccessor = UniProtUtils.getInstance()
                                                              .useRESTApi()
                                                              //.withSingleTempFileCache();
                                                              .withSingleFileCache(new File("C:/Temp/uniprot_cache2.json"));

        //		String json = JSONHelper.prettyPrint(uniProtRESTAccessor.searchFor("ADH")
        //																.limit(1)
        //																.map(entity -> entity.get())
        //																.collect(Collectors.toList()));

        uniProtRESTAccessor.searchInHumanGenesFor("ACOT")
                           .forEach(accessor ->
                           {
                               System.out.println(accessor.get()
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

    @Test
    @Ignore
    public void testGetById() throws Exception
    {
        List<Feature> features = UniProtUtils.getInstance()
                                             .useRESTApi()
                                             .withSingleTempFileCache()
                                             .getByUniProtId("P11310")
                                             .get()
                                             .getFeatures();

        System.out.println(JSONHelper.prettyPrint(features));

    }

    @Test
    @Ignore
    public void testGetVariants() throws Exception
    {
        UniProtRESTAccessor uniProtRESTAccessor = UniProtUtils.getInstance()
                                                              .useRESTApi()
                                                              .withLocalDirectoryCache();
        //                                                              .withSingleTempFileCache();
        //                                                              .withSingleFileCache(new File("C:/Temp/uniprot_cache2.json"));

        EntityAccessor entityAccessor = uniProtRESTAccessor.getByUniProtId("P11310");
        List<Binding> bindings = entityAccessor.getBindings();

        List<ModifiedResidue> modifiedResidues = entityAccessor.getModifiedResidues();
        List<ActiveSite> activeSite = entityAccessor.getActiveSites();

        System.out.println(JSONHelper.prettyPrint(activeSite));
        System.out.println(JSONHelper.prettyPrint(bindings));
        System.out.println(JSONHelper.prettyPrint(activeSite));
    }

    @Test
    @Ignore
    public void testLoadGenesFromFTPWithCache()
    {
        UniProtUtils.getInstance()
                    .useFTP()
                    .withLocalDirectoryCache()
                    .loadCurrentHumanProteome()
                    .getGenes()
                    .forEach(System.out::println);
    }

}
