package com.touchystems.testJUnit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.touchsystems.rest.Service;

public class JunitTest
{
	private Service classUnderTest;

	@Before
	public void setUp() throws Exception
	{
		classUnderTest = new Service();
	}

	@Test
	public void testGetMetricsAll()
	{
		/*Response when setMetricsID is called without params.
		 * 
		 * OutboundJaxrsResponse{status=400, reason=Bad Request, hasEntity=true, closed=false, buffered=false}";*/	

		assertEquals(400, classUnderTest.setMetricsID("").getStatus());
	}

	@Test
	public void testGetMetricsID()
	{
		/*Correct Json*/
		String json = "{\"system\": \"Oracle\"}";
		assertEquals(200, classUnderTest.getMetrics(json).getStatus());

		/*Wrong Json*/
		json = "{\"system2\": \"Oracle\"}";
		assertEquals(400, classUnderTest.getMetrics(json).getStatus());
	}
}
