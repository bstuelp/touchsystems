package com.touchsystems.DAO;

import java.util.List;

import com.touchsystems.entity.MetricSummary;
import com.touchsystems.entity.Metrics;

public interface IDao
{
	public List<Metrics> getMetrics(String system);
	
	public List<Metrics> getMetricsAll();
	
	public List<Metrics> getMetricsID(int id);
	
	public List<MetricSummary> getMetricSummary(MetricSummary m);
	
	public int createNewMetric(String system, String name);

	public int updMetrics(int id, String system, int date, String name, int value);
	
	public int getValue(int id);
	
	public int lastInsertID();
}
