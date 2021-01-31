package org.omnaest.genomics.uniprot.fasta;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.omnaest.utils.MatcherUtils;

public class UniProtFastaUtils
{
    public static interface UniprotFastaDNAIndexLoader
    {
        public UniprotFastaDNAIndex from(File file);

        public UniprotFastaDNAIndex from(InputStream inputStream);
    }

    public static interface UniprotFastaDNAIndex
    {
        public Optional<String> getDNASequenceForUniprotId(String uniprotId);
    }

    public static interface UniprotFastaProteomeIndexLoader
    {
        public UniprotFastaProteomeIndex from(File file);

        public UniprotFastaProteomeIndex from(InputStream inputStream);

        public UniprotFastaProteomeIndex from(byte[] data);
    }

    public static interface UniprotFastaProteomeIndex
    {
        public Optional<UniprotProtein> getProteinForUniprotId(String uniprotId);

        public Stream<UniprotProtein> getProteinsForGene(String gene);

        public Set<String> getGenes();
    }

    public static interface UniprotProtein
    {
        public String getSequence();

        public String getName();

        public String getGene();

        public String getUniprotId();
    }

    private static class UniprotProteinBuilder implements UniprotProtein
    {
        private String uniprotId;
        private String gene;
        private String name;
        private String sequence = "";

        public UniprotProteinBuilder(String uniprotId, String gene, String name)
        {
            super();
            this.uniprotId = uniprotId;
            this.gene = gene;
            this.name = name;
        }

        public UniprotProtein appendSequence(String sequence)
        {
            this.sequence += sequence;
            return this;
        }

        @Override
        public String getUniprotId()
        {
            return this.uniprotId;
        }

        @Override
        public String getGene()
        {
            return this.gene;
        }

        @Override
        public String getName()
        {
            return this.name;
        }

        @Override
        public String getSequence()
        {
            return this.sequence;
        }

        @Override
        public String toString()
        {
            return "UniprotProtein [uniprotId=" + this.uniprotId + ", gene=" + this.gene + ", name=" + this.name + ", sequence=" + this.sequence + "]";
        }

    }

    public static UniprotFastaProteomeIndexLoader loadFastaProteome()
    {
        return new UniprotFastaProteomeIndexLoader()
        {
            @Override
            public UniprotFastaProteomeIndex from(InputStream inputStream)
            {
                Map<String, UniprotProteinBuilder> uniprotIdToProtein = this.buildIndex(inputStream);
                Map<String, List<UniprotProteinBuilder>> geneToProtein = uniprotIdToProtein.values()
                                                                                           .stream()
                                                                                           .filter(protein -> !StringUtils.equals(protein.getGene(), "-"))
                                                                                           .collect(Collectors.groupingBy(UniprotProtein::getGene));

                return new UniprotFastaProteomeIndex()
                {
                    @Override
                    public Optional<UniprotProtein> getProteinForUniprotId(String uniprotId)
                    {
                        return Optional.ofNullable(uniprotIdToProtein.get(uniprotId));
                    }

                    @Override
                    public Stream<UniprotProtein> getProteinsForGene(String gene)
                    {
                        return Optional.ofNullable(geneToProtein.get(gene))
                                       .map(List::stream)
                                       .map(stream -> stream.map(element -> (UniprotProtein) element))
                                       .orElse(Stream.empty());
                    }

                    @Override
                    public Set<String> getGenes()
                    {
                        return geneToProtein.keySet();
                    }

                };
            }

            private Map<String, UniprotProteinBuilder> buildIndex(InputStream inputStream)
            {
                Map<String, UniprotProteinBuilder> uniprotIdToProtein = new HashMap<>();
                try
                {
                    LineIterator lineIterator = IOUtils.lineIterator(inputStream, StandardCharsets.UTF_8);
                    AtomicReference<String> currentUniprotId = new AtomicReference<>();
                    lineIterator.forEachRemaining(line ->
                    {
                        if (StringUtils.startsWith(line, ">"))
                        {
                            String[] tokens = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, "|");
                            if (tokens.length >= 3)
                            {
                                String uniprotId = tokens[1];
                                currentUniprotId.set(uniprotId);

                                String gene = MatcherUtils.matcher()
                                                          .ofRegEx("GN=([^\\s]+)")
                                                          .findInAnd(tokens[2])
                                                          .stream()
                                                          .findFirst()
                                                          .map(match -> match.getGroup(1))
                                                          .orElse(null);
                                String name = org.omnaest.utils.StringUtils.leftUntil(tokens[2], "OS=");

                                uniprotIdToProtein.put(uniprotId, new UniprotProteinBuilder(uniprotId, gene, name));
                            }
                        }
                        else if (currentUniprotId.get() != null)
                        {
                            Optional.ofNullable(uniprotIdToProtein.get(currentUniprotId.get()))
                                    .ifPresent(protein -> protein.appendSequence(line));
                        }
                    });
                }
                catch (IOException e)
                {
                    throw new IllegalStateException("Unable to read input stream", e);
                }
                return uniprotIdToProtein;
            }

            @Override
            public UniprotFastaProteomeIndex from(File file)
            {
                try
                {
                    return this.from(IOUtils.toBufferedInputStream(new FileInputStream(file)));
                }
                catch (IOException e)
                {
                    throw new IllegalArgumentException("Cannot read from file: " + file, e);
                }
            }

            @Override
            public UniprotFastaProteomeIndex from(byte[] data)
            {
                return this.from(new ByteArrayInputStream(data));
            }
        };
    }

    public static UniprotFastaDNAIndexLoader loadFastaDNA()
    {
        return new UniprotFastaDNAIndexLoader()
        {

            @Override
            public UniprotFastaDNAIndex from(File file)
            {
                try
                {
                    return this.from(IOUtils.toBufferedInputStream(new FileInputStream(file)));
                }
                catch (IOException e)
                {
                    throw new IllegalArgumentException("Cannot read from file: " + file, e);
                }
            }

            @Override
            public UniprotFastaDNAIndex from(InputStream inputStream)
            {
                Map<String, String> uniprotIdToDNA = this.buildIndex(inputStream);
                return new UniprotFastaDNAIndex()
                {
                    @Override
                    public Optional<String> getDNASequenceForUniprotId(String uniprotId)
                    {
                        return Optional.ofNullable(uniprotIdToDNA.get(uniprotId));
                    }
                };
            }

            private Map<String, String> buildIndex(InputStream inputStream)
            {
                Map<String, String> uniprotIdToDNA = new HashMap<>();
                try
                {
                    LineIterator lineIterator = IOUtils.lineIterator(inputStream, StandardCharsets.UTF_8);
                    AtomicReference<String> currentUniprotId = new AtomicReference<>();
                    lineIterator.forEachRemaining(line ->
                    {
                        if (StringUtils.startsWith(line, ">"))
                        {
                            String[] tokens = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, "|");
                            if (tokens.length >= 3)
                            {
                                String uniprotId = tokens[1];
                                currentUniprotId.set(uniprotId);
                                //                                String ensembleId = tokens[2]; 
                            }
                        }
                        else if (currentUniprotId.get() != null)
                        {
                            uniprotIdToDNA.merge(currentUniprotId.get(), line, (previousCode, additionalCode) -> previousCode + additionalCode);
                        }
                    });
                }
                catch (IOException e)
                {
                    throw new IllegalStateException("Unable to read input stream", e);
                }
                return uniprotIdToDNA;
            }
        };
    }
}
