package com.touchsystems.entity;

public class Metrics
{
	int id;
	String system;
	String name;
	int date;
	int value;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

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

	public int getDate()
	{
		return date;
	}

	public void setDate(int date)
	{
		this.date = date;
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
