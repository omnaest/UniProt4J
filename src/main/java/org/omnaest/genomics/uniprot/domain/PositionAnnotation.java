package org.omnaest.genomics.uniprot.domain;

import java.util.List;

public class PositionAnnotation
{

    protected String description;
    protected List<Integer> positions;

    public PositionAnnotation()
    {
        super();
    }

    public String getDescription()
    {
    	return this.description;
    }

    public List<Integer> getPositions()
    {
    	return this.positions;
    }

}