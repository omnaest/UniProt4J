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
package org.omnaest.uniprot.domain.raw;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.NONE)
public class Citation
{
	@XmlElement
	private String title;

	@XmlElementWrapper(name = "authorList")
	@XmlElement(name = "person")
	private List<Author> authors = new ArrayList<>();

	@XmlElement(name = "dbReference")
	private List<DBReference> dbReferences = new ArrayList<>();

	public String getTitle()
	{
		return this.title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public List<Author> getAuthors()
	{
		return this.authors;
	}

	public void setAuthors(List<Author> authors)
	{
		this.authors = authors;
	}

	public List<DBReference> getDbReferences()
	{
		return this.dbReferences;
	}

	public void setDbReferences(List<DBReference> dbReferences)
	{
		this.dbReferences = dbReferences;
	}

}
