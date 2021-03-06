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

public class IncidentRepository {
	public static void createIncidentTable() throws SQLException {
		try {
			DBConnection.checkPath();
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(DBConnection.PATH);
			Statement stm = con.createStatement();
			String drop = "DROP TABLE IF EXISTS INCIDENT;";
			stm.executeUpdate(drop);
			String query = "CREATE TABLE IF NOT EXISTS INCIDENT"
					+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
					+ "Message TEXT NOT NULL," + "Date TEXT NOT NULL,"
					+ "File TEXT NOT NULL" + ")";
			stm.executeUpdate(query);
			stm.close();
		} catch (SQLException | ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public static void insertIncident(Incident incident) throws SQLException {
		insertIncident(incident.getMessage(), incident.getDate(),
				incident.getFile());
	}

	public static void insertIncident(String message, Date date, String file)
			throws SQLException {
		try {
			DBConnection.checkPath();
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(DBConnection.PATH);
			con.setAutoCommit(false);
			Statement stm = con.createStatement();
			String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(date);
			String sql = "INSERT INTO INCIDENT (Message,Date,File)"
					+ "VALUES ('" + message + "','" + formatted + "','"
					+ file + "');";
			stm.executeUpdate(sql);
			stm.close();
			con.commit();
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public static Collection<Incident> getIncident(Integer id)
			throws SQLException {
		String sql = "SELECT * FROM INCIDENT WHERE ID = '" + id + "';";
		return runQuery(sql);
	}

	public static Collection<Incident> getAllIncidents() throws SQLException {
		String sql = "SELECT * FROM INCIDENT;";
		return runQuery(sql);
	}

	public static Collection<Incident> runQuery(String sql) throws SQLException {
		try {
			DBConnection.checkPath();
			Collection<Incident> result = new ArrayList<Incident>();
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(DBConnection.PATH);
			con.setAutoCommit(false);
			Statement stm = con.createStatement();
			ResultSet res = stm.executeQuery(sql);
			while (res.next()) {
				Integer id = res.getInt("ID");
				String message = res.getString("Message");
				String file = res.getString("File");
				String a = res.getString("Date");
				SimpleDateFormat formatted = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = formatted.parse(a);
				Incident newIncident = new Incident(id, message, date, file);
				result.add(newIncident);
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
