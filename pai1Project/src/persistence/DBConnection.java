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
	public static void createDB(){
		Connection c = null;
		String path = PATH==null?"jdbc:sqlite:test.db":PATH;
		setPath(path);
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection(path);
	      
	      HashRepository.createHashesTable();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");

	}
	
	
	public static void checkPath() {
		if(PATH == null){
			throw new IllegalArgumentException("La base de datos no ha sido creada");
		}
		
	}
	
}
