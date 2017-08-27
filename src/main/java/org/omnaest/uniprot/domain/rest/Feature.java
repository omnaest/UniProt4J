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

import java.util.function.Predicate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlAccessorType(XmlAccessType.NONE)
public class Feature
{
	@XmlAttribute
	private String type;

	@XmlAttribute
	private String description;

	@XmlAttribute
	private String evidence;

	@XmlElement
	private Location location;

	@XmlAnyElement
	private Object content;

	public enum Type
	{
		METAL_BINDING_SITE("metal ion-binding site"), BINDING_SITE("binding site");

		private Predicate<String> predicate;

		private Type(String filter)
		{
			this.predicate = type -> StringUtils.containsIgnoreCase(type, filter);
		}

		public Predicate<String> getPredicate()
		{
			return this.predicate;
		}
	}

	@JsonIgnore
	public boolean isOfType(Type type)
	{
		return type	.getPredicate()
					.test(this.type);
	}

	public Location getLocation()
	{
		return this.location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getEvidence()
	{
		return this.evidence;
	}

	public void setEvidence(String evidence)
	{
		this.evidence = evidence;
	}

	public Object getContent()
	{
		return this.content;
	}

	public void setContent(Object content)
	{
		this.content = content;
	}

	@JsonIgnore
	public boolean isMarkedAsRemoved()
	{
		return StringUtils.equalsIgnoreCase(this.description, "Removed");
	}

	@Override
	public String toString()
	{
		return "Feature [type=" + this.type + ", description=" + this.description + ", evidence=" + this.evidence + ", location=" + this.location + ", content="
				+ this.content + "]";
	}

}
