package org.omnaest.genomics.uniprot.ftp;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.genomics.uniprot.ftp.UniProtFTPUtils;

/**
 * @see UniProtFTPUtils
 * @author omnaest
 */
public class UniProtFTPUtilsTest
{

    @Test
    @Ignore
    public void testLoadCurrentHumanProteomeFasta() throws Exception
    {
        System.out.println(IOUtils.toString(UniProtFTPUtils.loadCurrentHumanProteomeFasta(), StandardCharsets.UTF_8));
    }

    @Test
    @Ignore
    public void testLoadCurrentHumanDNAFasta() throws Exception
    {
        System.out.println(IOUtils.toString(UniProtFTPUtils.loadCurrentHumanDNAFasta(), StandardCharsets.UTF_8));
    }

}
