# UniProt4J
Utilities for the REST API of http://www.uniprot.org and FTP ftp://ftp.uniprot.org

## Example

    List<Feature> features = UniProtUtils.getInstance()
                                         .useRESTApi()
                                         .withSingleTempFileCache()
                                         .getByUniProtId("P11310")
                                         .get()
                                         .getFeatures();

# Maven Snapshots

    <dependency>
      <groupId>org.omnaest.genomics</groupId>
      <artifactId>UniProt4J</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>
    
    <repositories>
        <repository>
            <id>ossrh</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>