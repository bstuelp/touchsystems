package com.touchsystems.rest;

import javax.ws.rs.core.Response;

public interface IService 
{
	public Response getMetrics(String json);

	public Response getMetricsAll();

	public Response getMetricsID(int id);
	
	public Response getMetricsSummary(String json);
	
	public Response setMetricsID(String json);
	
	public Response updMetricsID(int id, String json);
	
}
