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
package org.omnaest.genomics.uniprot.domain;

import java.util.Collection;
import java.util.stream.Collectors;

public class Binding extends PositionAnnotation
{
	private String			compound;
	public Binding(String compound, String description, Collection<Integer> positions)
	{
		super();
		this.compound = compound;
		this.description = description;
		this.positions = positions	.stream()
									.distinct()
									.collect(Collectors.toList());
	}

	public String getCompound()
	{
		return this.compound;
	}

	@Override
	public String toString()
	{
		return "Binding [compound=" + this.compound + ", description=" + this.description + ", positions=" + this.positions + "]";
	}

}
