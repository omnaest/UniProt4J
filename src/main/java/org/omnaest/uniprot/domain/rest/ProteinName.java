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
package org.omnaest.uniprot.domain.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class ProteinName
{
	@XmlElement
	private String fullName;

	@XmlElement
	private String shortName;

	@XmlElement
	private String ecNumber;

	public String getShortName()
	{
		return this.shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public String getFullName()
	{
		return this.fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getEcNumber()
	{
		return this.ecNumber;
	}

	public void setEcNumber(String ecNumber)
	{
		this.ecNumber = ecNumber;
	}

	@Override
	public String toString()
	{
		return "ProteinName [fullName=" + this.fullName + ", shortName=" + this.shortName + ", ecNumber=" + this.ecNumber + "]";
	}

}
