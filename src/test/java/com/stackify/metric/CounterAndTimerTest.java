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

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.stackify.metric.impl.MetricMonitorType;

/**
 * CounterAndTimer JUnit Test
 * @author Eric Martin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CounterAndTimer.class, Counter.class, Timer.class})
public class CounterAndTimerTest {

	/**
	 * testConstructor
	 */
	@Test
	public void testConstructor() {
		String category = "category";
		String name = "name";
		CounterAndTimer metric = new CounterAndTimer(category, name);
		
		Assert.assertEquals(category, metric.getCounter().getIdentity().getCategory());
		Assert.assertEquals(name, metric.getCounter().getIdentity().getName());
		Assert.assertEquals(MetricMonitorType.COUNTER, metric.getCounter().getIdentity().getType());

		Assert.assertEquals(category, metric.getTimer().getIdentity().getCategory());
		Assert.assertEquals(name + " Time", metric.getTimer().getIdentity().getName());
		Assert.assertEquals(MetricMonitorType.TIMER, metric.getTimer().getIdentity().getType());
	}
	
	/**
	 * testStart
	 * @throws Exception 
	 */
	@Test
	public void testStart() throws Exception {
		Counter counter = Mockito.mock(Counter.class);
		PowerMockito.whenNew(Counter.class).withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(counter);

		Timer timer = Mockito.mock(Timer.class);
		PowerMockito.whenNew(Timer.class).withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(timer);
		
		CounterAndTimer metric = new CounterAndTimer("category", "name");
		
		Date start = new Date();

		metric.start(start);

		Mockito.verify(counter).increment();
		Mockito.verify(timer).start(start);
	}
	
	/**
	 * testStartWithNull
	 * @throws Exception 
	 */
	@Test
	public void testStartWithNull() throws Exception {
		Counter counter = Mockito.mock(Counter.class);
		PowerMockito.whenNew(Counter.class).withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(counter);

		Timer timer = Mockito.mock(Timer.class);
		PowerMockito.whenNew(Timer.class).withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(timer);

		CounterAndTimer metric = new CounterAndTimer("category", "name");
		metric.start(null);
				
		Mockito.verifyZeroInteractions(counter);
		Mockito.verifyZeroInteractions(timer);
	}
	
	/**
	 * testStartMs
	 * @throws Exception 
	 */
	@Test
	public void testStartMs() throws Exception {
		Counter counter = Mockito.mock(Counter.class);
		PowerMockito.whenNew(Counter.class).withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(counter);

		Timer timer = Mockito.mock(Timer.class);
		PowerMockito.whenNew(Timer.class).withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(timer);

		CounterAndTimer metric = new CounterAndTimer("category", "name");
		
		long startMs = System.currentTimeMillis();

		metric.startMs(startMs);
				
		Mockito.verify(counter).increment();
		Mockito.verify(timer).startMs(startMs);
	}
	
	/**
	 * testDurationMs
	 * @throws Exception 
	 */
	@Test
	public void testDurationMs() throws Exception {
		Counter counter = Mockito.mock(Counter.class);
		PowerMockito.whenNew(Counter.class).withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(counter);

		Timer timer = Mockito.mock(Timer.class);
		PowerMockito.whenNew(Timer.class).withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(timer);

		CounterAndTimer metric = new CounterAndTimer("category", "name");
		
		long durationMs = 5000;

		metric.durationMs(durationMs);
				
		Mockito.verify(counter).increment();
		Mockito.verify(timer).durationMs(durationMs);
	}
	
	/**
	 * testAutoReportZeroValue
	 * @throws Exception 
	 */
	@Test
	public void testAutoReportZeroValue() throws Exception {
		Counter counter = Mockito.mock(Counter.class);
		PowerMockito.whenNew(Counter.class).withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(counter);

		Timer timer = Mockito.mock(Timer.class);
		PowerMockito.whenNew(Timer.class).withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(timer);

		CounterAndTimer metric = new CounterAndTimer("category", "name");
		
		metric.autoReportZeroValue();
				
		Mockito.verify(counter).autoReportZeroValue();
		Mockito.verify(timer).autoReportZeroValue();
	}
}
