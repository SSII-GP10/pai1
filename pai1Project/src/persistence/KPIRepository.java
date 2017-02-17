package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

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
			String drop = "DROP TABLE IF EXISTS KPI;";
			st.executeUpdate(drop);
			String query = "CREATE TABLE IF NOT EXISTS KPI"+
			"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
			"Ratio DECIMAL NOT NULL,"+
			
			"Negatives NUMERIC NOT NULL,"
			+"Positives NUMERIC NOT NULL,"+
			"ReportDate TEXT NOT NULL"
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
		insertKPI(kpi.getRatio(), kpi.getPositives(),kpi.getNegatives());
	}
	/**
	 * Insert a kpi into de DB with current date
	 * @param id :  the given id for the kpi
	 * @param ratio : ratio double of the kpi
	 * @param positives : Number of files that were unchanged
	 * @param negatives : Number of files that were changed
	 * @throws IllegalArgumentException
	 */
	public static void insertKPI(Double ratio, Integer positives, Integer negatives) throws IllegalArgumentException{
		DBConnection.checkPath();
		Connection c = null;
		Statement st = null;
		Date now = new Date(System.currentTimeMillis());
		String formatted =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection(DBConnection.PATH);
				c.setAutoCommit(false);
				st = c.createStatement();
				String sql = "INSERT INTO KPI (Ratio,Negatives,Positives,ReportDate)"+
				"VALUES ('"+ratio+"','"+negatives+"','"+positives+"','"+formatted+"');";
				
				st.executeUpdate(sql);
				st.close();
				c.commit();
				c.close();
				
			} catch (Exception e) {
				 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			     System.exit(0);
			}
			System.out.println("KPI Inserted");
	
	} 
	
	/**
	 * 
	 * @param id: the id of the kpi 
	 * @return give a collection with all the kpis that matches the given id there should be only 0...1 element.
	 */
	
	public static Collection<KPI> getKPI(Integer id){ 
		String sql = "SELECT * FROM KPI WHERE ID = '"+id+"';";
		return runQuery(sql);
	}
	
	/**
	 * 
	 * @return retrieves a collection with all the kpis of the system
	 */
	public static Collection<KPI> getAllKPI(){
		String sql = "SELECT * FROM KPI;";
		return runQuery(sql);
	}
	/**
	 * 
	 * @return A collection of the KPI that has a ReportDate of the same month that current month
	 */
	
	public static Collection<KPI> getKPIOfCurrentMonth(){
		
		Collection<KPI> result = getAllKPI();
		Calendar now = new GregorianCalendar();
		now.setTime(new Date(System.currentTimeMillis()));
		
		int month = now.get(Calendar.MONTH);
		result = result.stream()
						.filter(x ->{
							Calendar cal= new GregorianCalendar();
							cal.setTime(x.getReportDate());
							int xmonth =cal.get(GregorianCalendar.MONTH);
							return xmonth == month;
								})
						.collect(Collectors.toList());
		System.out.println(result);
		return result;
	}
	
	/**
	 * 
	 * @param sql, SQL query to execute OVER THE TABLE KPI
	 * @return a collection of the kpis once the given query has been run
	 */
	public static Collection<KPI> runQuery(String sql){
		DBConnection.checkPath();
		Connection c = null;
		Statement st = null;
		ResultSet res = null;
		Collection<KPI> result = new ArrayList<KPI>();
		SimpleDateFormat formatted =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(DBConnection.PATH);
			c.setAutoCommit(false);
			st = c.createStatement();
			res = st.executeQuery(sql);
			
			while(res.next()){
			Integer id = res.getInt("ID");
			Double ratio = res.getDouble("Ratio");
			Integer positives = res.getInt("Positives");
			Integer negatives = res.getInt("Negatives");
			Date date = formatted.parse(res.getString("ReportDate"));
			KPI newkpi = new KPI(id,ratio,positives,negatives,date);
			result.add(newkpi);
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
