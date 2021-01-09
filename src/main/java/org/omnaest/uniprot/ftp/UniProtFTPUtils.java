package org.omnaest.uniprot.ftp;

import java.io.InputStream;

import org.omnaest.utils.ftp.FTPUtils;
import org.omnaest.utils.zip.ZipUtils;

/**
 * Access helper to files of ftp.uniprot.org
 * 
 * @author omnaest
 */
public class UniProtFTPUtils
{
    public static InputStream loadCurrentHumanProteomeFasta()
    {
        try
        {
            return ZipUtils.read()
                           .fromGzip(FTPUtils.load()
                                             .withAnonymousCredentials()
                                             .from("ftp.uniprot.org",
                                                   "/pub/databases/uniprot/current_release/knowledgebase/reference_proteomes/Eukaryota/UP000005640_9606.fasta.gz")
                                             .get()
                                             .asInputStream())
                           .asInputStream();
        }
        catch (Exception e)
        {
            throw new IllegalStateException("Unable to retrieve human proteome fasta file from uniprot ftp", e);
        }
    }

    public static InputStream loadCurrentHumanDNAFasta()
    {
        try
        {
            return ZipUtils.read()
                           .fromGzip(FTPUtils.load()
                                             .withAnonymousCredentials()
                                             .from("ftp.uniprot.org",
                                                   "pub/databases/uniprot/current_release/knowledgebase/reference_proteomes/Eukaryota/UP000005640_9606_DNA.fasta.gz")
                                             .get()
                                             .asInputStream())
                           .asInputStream();
        }
        catch (Exception e)
        {
            throw new IllegalStateException("Unable to retrieve human DNA fasta file from uniprot ftp", e);
        }
    }
}
