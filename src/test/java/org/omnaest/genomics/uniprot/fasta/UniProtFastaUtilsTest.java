/*******************************************************************************
 * Copyright 2021 Danny Kunz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.omnaest.genomics.uniprot.fasta;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.genomics.uniprot.fasta.UniProtFastaUtils;
import org.omnaest.genomics.uniprot.fasta.UniProtFastaUtils.UniprotFastaProteomeIndex;

public class UniProtFastaUtilsTest
{

    @Test
    @Ignore
    public void testLoadFastaProteome() throws Exception
    {
        UniprotFastaProteomeIndex index = UniProtFastaUtils.loadFastaProteome()
                                                           .from(new File("C:\\Z\\databases\\uniprot\\UP000005640_9606.fasta\\UP000005640_9606.fasta"));
        index.getGenes()
             .forEach(System.out::println);

        index.getProteinsForGene("SLC10A2")
             .forEach(protein ->
             {
                 System.out.println(protein);
             });
    }

}
