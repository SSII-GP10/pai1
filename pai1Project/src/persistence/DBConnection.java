package persistence;

import java.sql.SQLException;

public class DBConnection {
	public static String PATH;
	
	public DBConnection(){
		PATH = null;
	}
	
	/**
	 * Create Database if doesn't exists;
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void createDB() throws SQLException{
		if(PATH == null){
			PATH = "jdbc:sqlite:hid.db";
			try {
				Class.forName("org.sqlite.JDBC");
				HashRepository.createHashesTable();
				IncidentRepository.createIncidentTable();
				KPIRepository.createKPITable();
			} catch (ClassNotFoundException | SQLException e) {
				throw new SQLException("Error: Cant connect to DB.");
			}
		}
	}
	
	public static void checkPath() throws SQLException {
		if(PATH == null){
			throw new SQLException("Error: The DB not exist.");
		}
	}
	
}
