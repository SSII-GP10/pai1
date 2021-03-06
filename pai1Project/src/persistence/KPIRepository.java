package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

import domain.Incident;
import domain.KPI;

public class KPIRepository {
	public static void createKPITable() throws SQLException {
		try {
			DBConnection.checkPath();
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(DBConnection.PATH);
			Statement stm = con.createStatement();
			String drop = "DROP TABLE IF EXISTS KPI;";
			stm.executeUpdate(drop);
			String query = "CREATE TABLE IF NOT EXISTS KPI"
					+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
					+ "Ratio DOUBLE NOT NULL," + "Negatives NUMERIC NOT NULL,"
					+ "Positives NUMERIC NOT NULL,"
					+ "ReportDate TEXT NOT NULL" + ")";
			stm.executeUpdate(query);
			stm.close();
		} catch (SQLException | ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public static void insertKPI(KPI kpi) throws SQLException {
		insertKPI(kpi.getRatio(), kpi.getPositives(), kpi.getNegatives(), kpi.getReportDate());
	}

	public static void insertKPI(Double ratio, Integer positives,
			Integer negatives, Date date) throws SQLException {
		try {
			DBConnection.checkPath();
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(DBConnection.PATH);
			con.setAutoCommit(false);
			Statement stm = con.createStatement();
			String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			.format(date);
			String sql = "INSERT INTO KPI (Ratio,Negatives,Positives,ReportDate)"
					+ "VALUES ('"
					+ ratio
					+ "','"
					+ negatives
					+ "','"
					+ positives + "','" + formatted + "');";
			stm.executeUpdate(sql);
			stm.close();
			con.commit();
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public static Collection<KPI> getKPI(Integer id) throws SQLException {
		String sql = "SELECT * FROM KPI WHERE ID = '" + id + "';";
		return runQuery(sql);
	}

	public static Collection<KPI> getAllKPI() throws SQLException{
		String sql = "SELECT * FROM KPI;";
		return runQuery(sql);
	}
	
	public static Collection<KPI> getKPIOfCurrentMonth() throws SQLException{
		Collection<KPI> result = getAllKPI();
		Calendar now = new GregorianCalendar();
		now.setTime(new Date(System.currentTimeMillis()));
		int month = now.get(Calendar.MONTH);
		result = result.stream().filter(x -> {
			Calendar cal = new GregorianCalendar();
			cal.setTime(x.getReportDate());
			int xmonth = cal.get(GregorianCalendar.MONTH);
			return xmonth == month;
		}).collect(Collectors.toList());
		return result;
	}

	public static Collection<KPI> runQuery(String sql) throws SQLException{
		try {
			DBConnection.checkPath();
			Collection<KPI> result = new ArrayList<KPI>();
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(DBConnection.PATH);
			con.setAutoCommit(false);
			Statement stm = con.createStatement();
			ResultSet res = stm.executeQuery(sql);
			while (res.next()) {
				Integer id = res.getInt("ID");
				Double ratio = res.getDouble("Ratio");
				Integer positives = res.getInt("Positives");
				Integer negatives = res.getInt("Negatives");
				SimpleDateFormat formatted = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = formatted.parse(res.getString("ReportDate"));
				KPI newkpi = new KPI(id, ratio, positives, negatives, date);
				result.add(newkpi);
			}
			res.close();
			stm.close();
			con.close();
			return result;
		} catch (SQLException | ClassNotFoundException | ParseException e) {
			throw new SQLException(e.getMessage());
		}
	}
	
}
