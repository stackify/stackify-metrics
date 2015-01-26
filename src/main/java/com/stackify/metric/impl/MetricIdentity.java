/*
 * Copyright 2014 Stackify
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stackify.metric.impl;

import com.stackify.api.common.util.Preconditions;

/**
 * MetricIdentity
 * @author Eric Martin
 */
public class MetricIdentity {

	/**
	 * Metric category
	 */
	private final String category;
	
	/**
	 * Metric name
	 */
	private final String name;
	
	/**
	 * Metric monitor type
	 */
	private final MetricMonitorType type;
	
	/**
	 * Constructor
	 * @param category Metric category
	 * @param name Metric name
	 * @param type Metric monitor type
	 */
	public MetricIdentity(final String category, final String name, final MetricMonitorType type) {
		Preconditions.checkNotNull(category);
		Preconditions.checkArgument(!category.isEmpty());
		Preconditions.checkNotNull(name);
		Preconditions.checkArgument(!name.isEmpty());
		Preconditions.checkNotNull(type);
		
		this.category = category;
		this.name = name;
		this.type = type;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public MetricMonitorType getType() {
		return type;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MetricIdentity))
			return false;
		MetricIdentity other = (MetricIdentity) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MetricIdentity [category=" + category + ", name=" + name
				+ ", type=" + type + "]";
	}
}
