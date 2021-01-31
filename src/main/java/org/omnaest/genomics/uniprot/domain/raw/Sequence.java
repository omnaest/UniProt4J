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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.NONE)
public class Sequence
{
	@XmlAttribute
	private String length;

	@XmlAttribute
	private String mass;

	@XmlAttribute
	private String checksum;

	@XmlAttribute
	private String modified;

	@XmlAttribute
	private String version;

	@XmlAttribute
	private String precursor;

	@XmlValue
	private String value;

	public String getLength()
	{
		return this.length;
	}

	public void setLength(String length)
	{
		this.length = length;
	}

	public String getMass()
	{
		return this.mass;
	}

	public void setMass(String mass)
	{
		this.mass = mass;
	}

	public String getChecksum()
	{
		return this.checksum;
	}

	public void setChecksum(String checksum)
	{
		this.checksum = checksum;
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

	public String getPrecursor()
	{
		return this.precursor;
	}

	public void setPrecursor(String precursor)
	{
		this.precursor = precursor;
	}

	public String getValue()
	{
		return this.value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
