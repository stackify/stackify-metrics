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
package com.stackify.metric;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.stackify.api.common.AppIdentityService;
import com.stackify.metric.impl.MetricBackgroundService;
import com.stackify.metric.impl.MetricCollector;
import com.stackify.metric.impl.MetricMonitorService;
import com.stackify.metric.impl.MetricSender;

/**
 * MetricManager JUnit Test
 * @author Eric Martin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MetricManager.class, AppIdentityService.class, MetricMonitorService.class, MetricSender.class, MetricBackgroundService.class})
public class MetricManagerTest {

	/**
	 * testGetCollectorAndShutdown
	 * @throws Exception 
	 */
	@Test
	public void testGetCollectorAndShutdown() throws Exception {
		AppIdentityService ais = Mockito.mock(AppIdentityService.class);
		PowerMockito.whenNew(AppIdentityService.class).withAnyArguments().thenReturn(ais);

		MetricMonitorService mms = Mockito.mock(MetricMonitorService.class);
		PowerMockito.whenNew(MetricMonitorService.class).withAnyArguments().thenReturn(mms);

		MetricSender sender = Mockito.mock(MetricSender.class);
		PowerMockito.whenNew(MetricSender.class).withAnyArguments().thenReturn(sender);

		MetricBackgroundService background = PowerMockito.mock(MetricBackgroundService.class);
		PowerMockito.whenNew(MetricBackgroundService.class).withAnyArguments().thenReturn(background);
		
		MetricManager.shutdown();
		
		Mockito.verifyZeroInteractions(background);

		MetricCollector collector1 = MetricManager.getCollector();
		Assert.assertNotNull(collector1);
		
		Mockito.verify(background).start();
		
		MetricCollector collector2 = MetricManager.getCollector();
		Assert.assertNotNull(collector2);

		Assert.assertEquals(collector1, collector2);
		
		MetricManager.shutdown();
		
		Mockito.verify(background).stop();
	}
}
