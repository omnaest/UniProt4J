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
package org.omnaest.genomics.uniprot.ftp;

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
                                                   "/pub/databases/uniprot/current_release/knowledgebase/reference_proteomes/Eukaryota/UP000005640/UP000005640_9606.fasta.gz")
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
                                                   "pub/databases/uniprot/current_release/knowledgebase/reference_proteomes/Eukaryota/UP000005640/UP000005640_9606_DNA.fasta.gz")
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
