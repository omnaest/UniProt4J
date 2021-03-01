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
import java.util.List;
import java.util.stream.Collectors;

import org.omnaest.utils.JSONHelper;

public class MetalBinding
{
	private String			metal;
	private String			type;
	private List<Integer>	positions;

	public MetalBinding(String metal, String type, Collection<Integer> positions)
	{
		super();
		this.metal = metal;
		this.type = type;
		this.positions = positions	.stream()
									.distinct()
									.sorted()
									.collect(Collectors.toList());
	}

	public String getType()
	{
		return this.type;
	}

	public String getMetal()
	{
		return this.metal;
	}

	public List<Integer> getPositions()
	{
		return this.positions;
	}

	@Override
	public String toString()
	{
		return JSONHelper.prettyPrint(this);
	}

}
