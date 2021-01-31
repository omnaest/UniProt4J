package org.omnaest.genomics.uniprot.domain;

import java.util.Collection;

public class ModifiedResidue extends Binding
{

    public ModifiedResidue(String compound, String description, Collection<Integer> positions)
    {
        super(compound, description, positions);
    }

    @Override
    public String toString()
    {
        return "ModifiedResidue [getCompound()=" + this.getCompound() + ", getDescription()=" + this.getDescription() + ", getPositions()="
                + this.getPositions() + "]";
    }

}
