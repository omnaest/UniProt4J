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
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class Protein
{
	@XmlElement
	private ProteinName recommendedName;

	@XmlElement(name = "alternativeName")
	private List<ProteinName> alternativeNames = new ArrayList<>();

	public ProteinName getRecommendedName()
	{
		return this.recommendedName;
	}

	public void setRecommendedName(ProteinName recommendedName)
	{
		this.recommendedName = recommendedName;
	}

	public List<ProteinName> getAlternativeNames()
	{
		return this.alternativeNames;
	}

	public void setAlternativeNames(List<ProteinName> alternativeNames)
	{
		this.alternativeNames = alternativeNames;
	}

	@Override
	public String toString()
	{
		return "Protein [recommendedName=" + this.recommendedName + ", alternativeNames=" + this.alternativeNames + "]";
	}

}
