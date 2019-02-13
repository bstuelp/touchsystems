package com.touchsystems.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.touchsystems.DAO.PersDao;
import com.touchsystems.entity.MetricSummary;
import com.touchsystems.entity.Metrics;

@Path("/webservice")
public class Service implements IService
{
	@GET
	@Path("/metrics")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getMetrics(String json)
	{
		PersDao pd = new PersDao();
		List<Metrics> temp = null;
		
		try
		{
			Gson g = new Gson();
			MetricSummary m = g.fromJson(json, MetricSummary.class);

			if (m.getSystem() == null)
			{
				return(Response.status(Response.Status.BAD_REQUEST).build());
			}

			temp = pd.getMetrics(m.getSystem());
		}
		catch (NullPointerException e)
		{
			return(Response.status(Response.Status.BAD_REQUEST).build());
		}

		//return(new Gson().toJson(temp));
		return(Response.status(Response.Status.OK).entity(temp).build());
	}

	@GET
	@Path("/metricsAll")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getMetricsAll()
	{
		PersDao pd = new PersDao();
		List<Metrics> temp = pd.getMetricsAll();

		return(Response.status(Response.Status.OK).entity(temp).build());
	}

	@GET
	@Path("/metrics/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getMetricsID(@PathParam("id") int id)
	{
		PersDao pd = new PersDao();
		List<Metrics> temp = pd.getMetricsID(id);
		if(temp.isEmpty())
		{
			return(Response.status(Response.Status.NOT_FOUND).entity("The specified metric was not found").build());
		}

		return(Response.status(Response.Status.OK).entity(temp).build());
	}

	@GET
	@Path("/metricsummary")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getMetricsSummary(String json)
	{
		PersDao pd = new PersDao();
		List<MetricSummary> tempMS = null;

		try
		{
			Gson g = new Gson();
			MetricSummary m = g.fromJson(json, MetricSummary.class);

			if (m.getSystem() == null)
			{
				return(Response.status(Response.Status.BAD_REQUEST).entity("Error: A required parameter was not supplied!!").build());
			}

			tempMS = pd.getMetricSummary(m);
		}
		catch (NullPointerException e)
		{
			return(Response.status(Response.Status.BAD_REQUEST).entity("Error: A required parameter was not supplied!!").build());
		}

		return(Response.status(Response.Status.OK).entity(tempMS).build());
	}

	@POST
	@Path("/metrics")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response setMetricsID(String json)
	{
		List<Metrics> temp = null;

		try
		{
			Gson g = new Gson();
			Metrics m = g.fromJson(json, Metrics.class);
			
			if (m.getSystem() == null || m.getName() == null)
			{
				return(Response.status(Response.Status.BAD_REQUEST).entity("Error: A required parameter was not supplied!!").build());
			}
			
			PersDao pd = new PersDao();
			int tempSetM = pd.createNewMetric(m.getSystem(), m.getName());
			
			if (tempSetM == -1)
			{
				return(Response.status(Response.Status.BAD_REQUEST).entity("Error: Duplicated data!!").build());
			}
			
			int id = pd.lastInsertID();
			temp = pd.getMetricsID(id);
		}
		catch (NullPointerException e)
		{
			return(Response.status(Response.Status.BAD_REQUEST).entity("Error: A required parameter was not supplied!!").build());
		}
		
		return(Response.status(Response.Status.OK).entity(temp).build());
	}

	@PUT
	@Path("/metrics/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response updMetricsID(@PathParam("id") int id, String json)
	{
		Gson g = new Gson();
		Metrics m = g.fromJson(json, Metrics.class);

		if (m.getSystem() == null || m.getName() == null || m.getDate() == 0)
		{
			return(Response.status(Response.Status.BAD_REQUEST).entity("Error: A required parameter was not supplied!!").build());
		}

		PersDao pd = new PersDao();
		int tempUp = pd.updMetrics(id, m.getSystem(), m.getDate(), m.getName(), m.getValue());

		if (tempUp == -1)
		{
			return(Response.status(Response.Status.BAD_REQUEST).entity("Error: Duplicated data!!").build());
		}

		List<Metrics> temp = pd.getMetricsID(id);

		return(Response.status(Response.Status.OK).entity(temp).build());
	}
}
