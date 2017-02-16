package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import domain.Incident;



public class IncidentRepository {
	/**
	 * Creates the database table Incident
	 * @throws IllegalArgumentException
	 */
	public static void createIncidentTable() throws IllegalArgumentException{
		DBConnection.checkPath();
		Statement st =null;
		Connection c= null;
		try {	
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(DBConnection.PATH);
			
			st = c.createStatement();
			String query = "CREATE TABLE INCIDENT"+
			"(ID INT PRIMARY KEY NOT NULL,"+
			"Message TEXT NOT NULL,"+
			"Date TEXT NOT NULL,"+
			"FileId INT NOT NULL,"+
			"FOREIGN KEY(FileId) REFERENCES HASHES(ID)"
			+ ")";
			st.executeUpdate(query);
			st.close();
		} catch (Exception e) {
			 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		     System.exit(0);
		}
		System.out.println("tabla creada correctamente");
	}
	
	/**
	 * Insert an incident into de DB
	 * @param incident: Incident type we want to save.
	 * @throws IllegalArgumentException
	 */
	public static void insertIncident(Incident incident) throws IllegalArgumentException{
		insertIncident(incident.getId(), incident.getMessage(), incident.getDate(),incident.getFileId());
	}
	/**
	 * Insert an incident into de DB
	 * @param id :  the given id for the incident
	 * @param message : message text of the incident
	 * @param date : Date when the incident was found.
	 * @param fileId : id of the Hash type that the incident references.
	 * @throws IllegalArgumentException
	 */
	public static void insertIncident(Integer id,String message, Date date, Integer fileId) throws IllegalArgumentException{
		DBConnection.checkPath();
		Connection c = null;
		Statement st = null;
		String formatted =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection(DBConnection.PATH);
				c.setAutoCommit(false);
				st = c.createStatement();
				String sql = "INSERT INTO INCIDENT (ID,Message,Date,FileId)"+
				"VALUES ("+id.toString()+",'"+message+"','"+formatted+"','"+fileId+"');";
				
				st.executeUpdate(sql);
				st.close();
				c.commit();
				c.close();
				
			} catch (Exception e) {
				 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			     System.exit(0);
			}
			System.out.println("Incident Inserted");
	
	} 
	
	/**
	 * 
	 * @param id: the name of the incident 
	 * @return give a collection with all the incidents that matches the given id there should be only 0...1 element.
	 */
	
	public static Collection<Incident> getIncident(Integer id){ 
		String sql = "SELECT * FROM INCIDENT WHERE ID = '"+id+"';";
		return runQuery(sql);
	}
	
	/**
	 * 
	 * @return retrieves a collection with all the incidents of the system.
	 */
	public static Collection<Incident> getAllIncidents(){
		String sql = "SELECT * FROM INCIDENT;";
		return runQuery(sql);
	}
	
	/**
	 * 
	 * @param sql, SQL query to execute OVER THE TABLE INCIDENT
	 * @return a collection of the kpis once the given query has been run
	 */
	public static Collection<Incident> runQuery(String sql){
		DBConnection.checkPath();
		Connection c = null;
		Statement st = null;
		ResultSet res = null;
		Collection<Incident> result = new ArrayList<Incident>();
		SimpleDateFormat formatted =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(DBConnection.PATH);
			c.setAutoCommit(false);
			st = c.createStatement();
			res = st.executeQuery(sql);
			
			while(res.next()){
			Integer id = res.getInt("ID");
			String message = res.getString("Message");
			Integer fileId = res.getInt("FileId");
			String a = res.getString("Date");
			System.out.println(a);
			Date date = formatted.parse(a);
			Incident newIncident = new Incident(id, message, date, fileId);
			result.add(newIncident);
		}
			res.close();
			st.close();
			c.close();
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		     System.exit(0);
		}
		return result;
	}
	
}
