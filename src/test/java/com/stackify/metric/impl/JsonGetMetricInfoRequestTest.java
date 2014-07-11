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

import org.junit.Assert;
import org.junit.Test;

/**
 * JsonGetMetricInfoRequest JUnit Test
 * @author Eric Martin
 */
public class JsonGetMetricInfoRequestTest {

	/**
	 * testBuilder
	 */
	@Test
	public void testBuilder() {
	    String category = "category";
	    String metricName = "metricName";
	    Integer deviceId = 12;
	    Integer deviceAppId = 13;
	    String appNameId = "appNameId";
	    Integer metricTypeId = 14;
		
	    JsonGetMetricInfoRequest.Builder builder = JsonGetMetricInfoRequest.newBuilder();
	    builder.category(category);
	    builder.metricName(metricName);
	    builder.deviceId(deviceId);
	    builder.deviceAppId(deviceAppId);
	    builder.appNameId(appNameId);
	    builder.metricTypeId(metricTypeId);
	    
	    JsonGetMetricInfoRequest request = builder.build();
	    
	    Assert.assertNotNull(request);
	    Assert.assertEquals(category, request.getCategory());
	    Assert.assertEquals(metricName, request.getMetricName());
	    Assert.assertEquals(deviceId, request.getDeviceId());
	    Assert.assertEquals(deviceAppId, request.getDeviceAppId());
	    Assert.assertEquals(appNameId, request.getAppNameId());
	    Assert.assertEquals(metricTypeId, request.getMetricTypeId());	    
	}
}
