package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import domain.Hash;

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
	      
	      createHashesTable();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");

	}
	
	/**
	 * Creates the database table HASHES
	 * @throws IllegalArgumentException
	 */
	public static void createHashesTable() throws IllegalArgumentException{
		checkPath(PATH);
		Statement st =null;
		Connection c= null;
		try {	
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(PATH);
			
			st = c.createStatement();
			String query = "CREATE TABLE HASHES"+
			"(ID INT PRIMARY KEY NOT NULL,"+
			"Hash TEXT NOT NULL,"+
			"Name TEXT NOT NULL)";
			st.executeUpdate(query);
			st.close();
		} catch (Exception e) {
			 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		     System.exit(0);
		}
		System.out.println("tabla creada correctamente");
	}
	
	/**
	 * Insert a hash into de DB
	 * @param hash: Hash type we want to save.
	 * @throws IllegalArgumentException
	 */
	public static void insertHash(Hash hash) throws IllegalArgumentException{
		insertHash(hash.getId(), hash.getHash(), hash.getName());
	}
	/**
	 * Insert a hash into de DB
	 * @param id :  the given id for the hash
	 * @param hash : hash string of the hash
	 * @param name : name in terms of path to the file of the hash
	 * @throws IllegalArgumentException
	 */
	public static void insertHash(Integer id,String hash, String name) throws IllegalArgumentException{
		checkPath(PATH);
		Connection c = null;
		Statement st = null;
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection(PATH);
				c.setAutoCommit(false);
				st = c.createStatement();
				String sql = "INSERT INTO HASHES (ID,Hash,Name)"+
				"VALUES ("+id.toString()+",'"+hash+"','"+name+"');";
				
				st.executeUpdate(sql);
				st.close();
				c.commit();
				c.close();
				
			} catch (Exception e) {
				 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			     System.exit(0);
			}
			System.out.println("Hash Inserted: "+hash);
	
	} 
	
	/**
	 * 
	 * @param name: the name of the hash 
	 * @return give a collecition with all the hashes that matches the given name, if the name is a path to 
	 * the file, there should be only 0...1 element.
	 */
	
	public static Collection<Hash> getHash(String name){ 
		String sql = "SELECT * FROM HASHES WHERE Name = '"+name+"';";
		return runQuery(sql);
	}
	
	/**
	 * 
	 * @return retrieves a collection with all the hashes of the system
	 */
	public static Collection<Hash> getAllHashes(){
		String sql = "SELECT * FROM HASHES;";
		return runQuery(sql);
	}
	
	/**
	 * 
	 * @param sql, SQL query to execute OVER THE TABLE HASHES
	 * @return a collection of the hashes once the given query has been run
	 */
	public static Collection<Hash> runQuery(String sql){
		checkPath(PATH);
		Connection c = null;
		Statement st = null;
		ResultSet res = null;
		Collection<Hash> result = new ArrayList<Hash>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(PATH);
			c.setAutoCommit(false);
			st = c.createStatement();
			res = st.executeQuery(sql);
			
			while(res.next()){
			Integer id = res.getInt("ID");
			String Hname = res.getString("Name");
			String hash = res.getString("Hash");
			Hash newHash = new Hash(id,Hname,hash);
			result.add(newHash);
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
	
	/**
	 * 
	 * @param hash
	 * @return true if the given Hash has the same hash that it had in db
	 */
	public static Boolean unchangedHash(Hash hash){
		Boolean res = false;
		Collection<Hash> col = getHash(hash.getName());
		List<Hash> result= col.stream().filter(x -> hash.getHash().equals(x.getHash()))
		.collect(Collectors.toList());
		if(!result.isEmpty())
			res = true;
		return res;
	}
	private static void checkPath(String path) {
		if(path == null){
			throw new IllegalArgumentException("La base de datos no ha sido creada");
		}
		
	}
	
}
