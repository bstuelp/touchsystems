package com.touchsystems.entity;

public class MetricSummary
{
	String system;
	String name;
	int from;
	int to;
	int value;
	
	public String getSystem()
	{
		return system;
	}
	
	public void setSystem(String system)
	{
		this.system = system;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getFrom()
	{
		return from;
	}
	
	public void setFrom(int from)
	{
		this.from = from;
	}

	public int getTo()
	{
		return to;
	}
	
	public void setTo(int to)
	{
		this.to = to;
	}
	
	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}
}
