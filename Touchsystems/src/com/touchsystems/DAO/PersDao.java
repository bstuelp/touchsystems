package com.touchsystems.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.touchsystems.entity.MetricSummary;
import com.touchsystems.entity.Metrics;

public class PersDao implements IDao
{
	static Connection objConn = null;

	public PersDao()
	{
	
	}

	@Override
	public List<Metrics> getMetrics(String system)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	 id, ");
		sb.append("	 system, ");
		sb.append("	 name, ");
		sb.append("	 date, ");
		sb.append("	 value ");
		sb.append("FROM ");
		sb.append("  metrics ");
		sb.append("WHERE ");
		sb.append("  system = ?");

		List<Metrics> listMetrics = new ArrayList<Metrics>();

		PreparedStatement pst;
		ResultSet rs;
		
		try
		{
			objConn = new Conn().openConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, system);

			rs = pst.executeQuery();

			while (rs.next())
			{
				Metrics item = new Metrics();
				item.setId(rs.getInt("id"));
				item.setSystem(rs.getString("system"));
				item.setName(rs.getString("name"));
				item.setDate(rs.getInt("date"));
				item.setValue(rs.getInt("value"));
                
				listMetrics.add(item);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (listMetrics);
	}

	@Override
	public List<Metrics> getMetricsAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	 id, ");
		sb.append("	 system, ");
		sb.append("	 name, ");
		sb.append("	 date, ");
		sb.append("	 value ");
		sb.append("FROM ");
		sb.append("  metrics");

		List<Metrics> listMetrics = new ArrayList<Metrics>();

		PreparedStatement pst;
		ResultSet rs;
		
		try
		{
			objConn = new Conn().openConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				Metrics item = new Metrics();
				item.setId(rs.getInt("id"));
				item.setSystem(rs.getString("system"));
				item.setName(rs.getString("name"));
				item.setDate(rs.getInt("date"));
				item.setValue(rs.getInt("value"));
                
				listMetrics.add(item);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (listMetrics);
	}

	@Override
	public List<Metrics> getMetricsID(int id)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	 id, ");
		sb.append("	 system, ");
		sb.append("	 name, ");
		sb.append("	 date, ");
		sb.append("	 value ");
		sb.append("FROM ");
		sb.append("  metrics ");
		sb.append("WHERE ");
		sb.append("  id = ?");

		List<Metrics> listMetrics = new ArrayList<Metrics>();

		PreparedStatement pst;
		ResultSet rs;
		
		try
		{
			objConn = new Conn().openConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, id);

			rs = pst.executeQuery();

			while (rs.next())
			{
				Metrics item = new Metrics();
				item.setId(rs.getInt("id"));
				item.setSystem(rs.getString("system"));
				item.setName(rs.getString("name"));
				item.setDate(rs.getInt("date"));
				item.setValue(rs.getInt("value"));
                
				listMetrics.add(item);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (listMetrics);
	}

	@Override
	public List<MetricSummary> getMetricSummary(MetricSummary m)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	 system, ");
		sb.append("	 name, ");
		sb.append("	 metricsummary.from, ");
		sb.append("	 metricsummary.to, ");
		sb.append("	 value ");
		sb.append("FROM ");
		sb.append("  metricsummary ");
		sb.append("WHERE ");
		sb.append("  system = ?");

		if (m.getName() != null)
		{
			sb.append("  and name = '" + m.getName() + "'");
		}
		
		if (m.getFrom() != 0)
		{
			sb.append("  and metricsummary.from >= " + m.getFrom());
		}
		
		if (m.getTo() != 0)
		{
			sb.append("  and metricsummary.to < " + m.getTo());
		}

		List<MetricSummary> listMetricSymmary = new ArrayList<MetricSummary>();

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conn().openConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, m.getSystem());

			rs = pst.executeQuery();

			while (rs.next())
			{
				MetricSummary item = new MetricSummary();
				item.setSystem(rs.getString("system"));
				item.setName(rs.getString("name"));
				item.setFrom(rs.getInt("from"));
				item.setTo(rs.getInt("to"));
				item.setValue(rs.getInt("value"));
                
				listMetricSymmary.add(item);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (listMetricSymmary);
	}

	@Override
	public int createNewMetric(String system, String name)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO metrics ");
		sb.append("	 (system, name, date, value) ");
		sb.append("VALUES ");
		sb.append("  (?, ?, ?, ?) ");
		
		PreparedStatement pst;
		int temp = -1;
		
		try
		{
			objConn = new Conn().openConn();

			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, system);
			pst.setString(2, name);
			pst.setLong(3, System.currentTimeMillis() / 1000L);
			pst.setInt(4, 1);

			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (MySQLIntegrityConstraintViolationException ex)
		{
			System.out.println("Unique key violation.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	@Override
	public int updMetrics(int id, String system, int date, String name, int value)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append("	 metrics ");
		sb.append("SET");
		sb.append("  system = ?, ");
		sb.append("  name = ?, ");
		sb.append("  date = ?, ");
		sb.append("  value = ? ");
		sb.append("WHERE ");
		sb.append("  id = ? ");

		PreparedStatement pst;
		int temp = -1;
		
		try
		{
			objConn = new Conn().openConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, system);
			pst.setString(2, name);
			pst.setLong(3, System.currentTimeMillis() / 1000L);

			if (value == 0)
			{
				value = this.getValue(id) + 1;
			}

			pst.setInt(4, value);
			pst.setInt(5, id);

			temp = pst.executeUpdate();			

			pst.close();
			objConn.close();
		}
		catch (MySQLIntegrityConstraintViolationException ex)
		{
			System.out.println("Unique key violation.");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	
	@Override
	public int getValue(int id)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	 value ");
		sb.append("FROM ");
		sb.append("  metrics ");
		sb.append("WHERE ");
		sb.append("  id = ?");

		PreparedStatement pst;
		ResultSet rs;
		int tempValue = 0;
		
		try
		{
			objConn = new Conn().openConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, id);

			rs = pst.executeQuery();

			while (rs.next())
			{
				tempValue = rs.getInt("value");
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (tempValue);
	}

	@Override
	public int lastInsertID()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT MAX(id) as id from metrics");
		
		PreparedStatement pst;
		ResultSet rs;
		int tempValue = 0;
		
		try
		{
			objConn = new Conn().openConn();
			pst = objConn.prepareStatement(sb.toString());

			rs = pst.executeQuery();

			while (rs.next())
			{
				tempValue = rs.getInt("id");
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return (tempValue);
	}
}