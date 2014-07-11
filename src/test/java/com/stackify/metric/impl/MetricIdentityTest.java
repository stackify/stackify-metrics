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

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Assert;
import org.junit.Test;

/**
 * MetricIdentity JUnit Test
 * @author Eric Martin
 */
public class MetricIdentityTest {

	/**
	 * testConstructor
	 */
	@Test
	public void testConstructor() {
		String category = "category";
		String name = "name";
		MetricMonitorType type = MetricMonitorType.COUNTER;
		
		MetricIdentity identity = new MetricIdentity(category, name, type);

		Assert.assertNotNull(identity);
		Assert.assertEquals(category, identity.getCategory());
		Assert.assertEquals(name, identity.getName());
		Assert.assertEquals(type, identity.getType());
		
		Assert.assertNotNull(identity.toString());
	}
	
	/**
	 * testEquals
	 */
	@Test
	public void testEquals() {
		EqualsVerifier.forClass(MetricIdentity.class).suppress(Warning.STRICT_INHERITANCE).verify();
	}
}
