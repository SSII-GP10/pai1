package persistence;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static String PATH= null;
	
	public static void setPath(String path){
		PATH = path;
	}
	/**
	 * Create Database if doesn't exists;
	 */
	@SuppressWarnings("unused")
	public static void createDB(){
		Connection c = null;
		String path = PATH==null?"jdbc:sqlite:hid.db":PATH;
		setPath(path);
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection(path);
	      
	      //Create the tables
	      HashRepository.createHashesTable();
	      KPIRepository.createKPITable();
	      IncidentRepository.createIncidentTable();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");

	}
	
	
	public static void checkPath() {
		String path = PATH==null?"jdbc:sqlite:hid.db":PATH;
		setPath(path);
		if(PATH == null){
			throw new IllegalArgumentException("La base de datos no ha sido creada");
		}
		
	}
	
}
