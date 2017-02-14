package pai1Project;

import java.sql.*;
public class DBConnection {
	public static String PATH= null;
	
	public static void setPath(String path){
		PATH = path;
	}
	public static void createDB(){
		Connection c = null;
		String path = PATH==null?"jdbc:sqlite:test.db":PATH;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection(path);
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");

	}
}
