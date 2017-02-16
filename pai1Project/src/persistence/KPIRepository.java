package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import domain.Hash;
import domain.KPI;



public class KPIRepository {
	/**
	 * Creates the database table KPI
	 * @throws IllegalArgumentException
	 */
	public static void createKPITable() throws IllegalArgumentException{
		DBConnection.checkPath();
		Statement st =null;
		Connection c= null;
		try {	
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(DBConnection.PATH);
			
			st = c.createStatement();
			String query = "CREATE TABLE KPI"+
			"(ID INT PRIMARY KEY NOT NULL,"+
			"Ratio DECIMAL NOT NULL,"+
			
			"Negatives NUMERIC NOT NULL,"
			+"Positives NUMERIC NOT NULL,"+
			"ReportDate TIMESTAMP NOT NULL"
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
	 * Insert a kpi into de DB
	 * @param kpi: KPI type we want to save.
	 * @throws IllegalArgumentException
	 */
	public static void insertKPI(KPI kpi) throws IllegalArgumentException{
		insertKPI(kpi.getId(), kpi.getRatio(), kpi.getPositives(),kpi.getNegatives());
	}
	/**
	 * Insert a kpi into de DB
	 * @param id :  the given id for the kpi
	 * @param ratio : ratio double of the kpi
	 * @param name : name in terms of path to the file of the hash
	 * @throws IllegalArgumentException
	 */
	public static void insertKPI(Integer id,Double ratio, Integer positives, Integer negatives) throws IllegalArgumentException{
		DBConnection.checkPath();
		Connection c = null;
		Statement st = null;
		Date now = new Date(System.currentTimeMillis());
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection(DBConnection.PATH);
				c.setAutoCommit(false);
				st = c.createStatement();
				String sql = "INSERT INTO KPI (ID,Ratio,Negatives,Positives,ReportDate)"+
				"VALUES ("+id.toString()+",'"+ratio+"','"+negatives+"','"+positives+"','"+now.toString()+"');";
				
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
		DBConnection.checkPath();
		Connection c = null;
		Statement st = null;
		ResultSet res = null;
		Collection<Hash> result = new ArrayList<Hash>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(DBConnection.PATH);
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
}
