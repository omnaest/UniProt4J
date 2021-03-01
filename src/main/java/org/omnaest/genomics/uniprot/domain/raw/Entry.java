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
/*

	Copyright 2017 Danny Kunz

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.


*/
package org.omnaest.genomics.uniprot.domain.raw;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.NONE)
public class Entry
{
	@XmlAttribute
	private String dataset;

	@XmlAttribute
	private String created;

	@XmlAttribute
	private String modified;

	@XmlAttribute
	private String version;

	@XmlElement
	private String name;

	@XmlElement
	private Protein protein;

	@XmlElement
	private List<String> accession = new ArrayList<>();

	@XmlElementWrapper(name = "gene")
	@XmlElement(name = "name")
	private List<Gene> genes = new ArrayList<>();

	@XmlElement(name = "organism")
	private List<Organism> organisms = new ArrayList<>();

	@XmlElement(name = "reference")
	private List<Reference> references = new ArrayList<>();

	@XmlElement(name = "comment")
	private List<Comment> comments = new ArrayList<>();

	@XmlElement(name = "dbReference")
	private List<DBReference> dbReferences = new ArrayList<>();

	@XmlElement(name = "feature")
	private List<Feature> features = new ArrayList<>();

	@XmlElement(name = "evidence")
	private List<Evidence> evidences = new ArrayList<>();

	@XmlElement
	private Sequence sequence;

	public Protein getProtein()
	{
		return this.protein;
	}

	public void setProtein(Protein protein)
	{
		this.protein = protein;
	}

	public Sequence getSequence()
	{
		return this.sequence;
	}

	public void setSequence(Sequence sequence)
	{
		this.sequence = sequence;
	}

	public List<Evidence> getEvidences()
	{
		return this.evidences;
	}

	public void setEvidences(List<Evidence> evidences)
	{
		this.evidences = evidences;
	}

	public List<Feature> getFeatures()
	{
		return this.features;
	}

	public void setFeatures(List<Feature> features)
	{
		this.features = features;
	}

	public List<Comment> getComments()
	{
		return this.comments;
	}

	public void setComments(List<Comment> comments)
	{
		this.comments = comments;
	}

	public List<DBReference> getDbReferences()
	{
		return this.dbReferences;
	}

	public void setDbReferences(List<DBReference> dbReferences)
	{
		this.dbReferences = dbReferences;
	}

	public List<Reference> getReferences()
	{
		return this.references;
	}

	public void setReferences(List<Reference> references)
	{
		this.references = references;
	}

	public List<Organism> getOrganisms()
	{
		return this.organisms;
	}

	public void setOrganisms(List<Organism> organisms)
	{
		this.organisms = organisms;
	}

	public List<Gene> getGenes()
	{
		return this.genes;
	}

	public void setGenes(List<Gene> genes)
	{
		this.genes = genes;
	}

	public String getDataset()
	{
		return this.dataset;
	}

	public void setDataset(String dataset)
	{
		this.dataset = dataset;
	}

	public String getCreated()
	{
		return this.created;
	}

	public void setCreated(String created)
	{
		this.created = created;
	}

	public String getModified()
	{
		return this.modified;
	}

	public void setModified(String modified)
	{
		this.modified = modified;
	}

	public String getVersion()
	{
		return this.version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<String> getAccession()
	{
		return this.accession;
	}

	public void setAccession(List<String> accession)
	{
		this.accession = accession;
	}

	@Override
	public String toString()
	{
		return "Entry [dataset=" + this.dataset + ", created=" + this.created + ", modified=" + this.modified + ", version=" + this.version + ", name="
				+ this.name + ", protein=" + this.protein + ", accession=" + this.accession + ", genes=" + this.genes + ", organisms=" + this.organisms
				+ ", references=" + this.references + ", comments=" + this.comments + ", dbReferences=" + this.dbReferences + ", features=" + this.features
				+ ", evidences=" + this.evidences + ", sequence=" + this.sequence + "]";
	}

}
