package org.omnaest.uniprot.domain;

import java.util.Collection;

public class ActiveSite extends Binding
{

    public ActiveSite(String compound, String description, Collection<Integer> positions)
    {
        super(compound, description, positions);
    }

    @Override
    public String toString()
    {
        return "ActiveSite [getCompound()=" + this.getCompound() + ", getDescription()=" + this.getDescription() + ", getPositions()=" + this.getPositions()
                + "]";
    }

}
