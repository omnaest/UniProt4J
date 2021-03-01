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
