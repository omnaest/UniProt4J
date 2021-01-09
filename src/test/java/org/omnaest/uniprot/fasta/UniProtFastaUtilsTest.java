package org.omnaest.uniprot.fasta;

import java.io.File;

import org.junit.Test;
import org.omnaest.uniprot.fasta.UniProtFastaUtils;
import org.omnaest.uniprot.fasta.UniProtFastaUtils.UniprotFastaProteomeIndex;

public class UniProtFastaUtilsTest
{

    @Test
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
