package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import domain.Hash;

public class HashRepository {
	public static void createHashesTable() throws SQLException {
		try {
			DBConnection.checkPath();
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(DBConnection.PATH);
			Statement stm = con.createStatement();
			String drop = "DROP TABLE IF EXISTS HASHES;";
			stm.executeUpdate(drop);
			String query = "CREATE TABLE IF NOT EXISTS HASHES"
					+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
					+ "Hash TEXT NOT NULL," + "Name TEXT NOT NULL)";
			stm.executeUpdate(query);
			stm.close();
		} catch (SQLException | ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public static void insertHash(Hash hash) throws SQLException {
		insertHash(hash.getHash(), hash.getName());
	}

	public static void insertHash(String hash, String name) throws SQLException {
		try {
			DBConnection.checkPath();
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(DBConnection.PATH);
			con.setAutoCommit(false);
			Statement stm = con.createStatement();
			String sql = "INSERT INTO HASHES (Hash,Name)" + "VALUES ('" + hash
					+ "','" + name + "');";
			stm.executeUpdate(sql);
			stm.close();
			con.commit();
			con.close();
		} catch (SQLException | ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}

	}

	public static Collection<Hash> getHash(String name) throws SQLException {
		String sql = "SELECT * FROM HASHES WHERE Name = '" + name + "';";
		return runQuery(sql);
	}

	public static Collection<Hash> getAllHashes() throws SQLException {
		String sql = "SELECT * FROM HASHES;";
		return runQuery(sql);
	}

	public static Collection<Hash> runQuery(String sql) throws SQLException {
		try {
			DBConnection.checkPath();
			Collection<Hash> result = new ArrayList<Hash>();
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(DBConnection.PATH);
			con.setAutoCommit(false);
			Statement stm = con.createStatement();
			ResultSet res = stm.executeQuery(sql);
			while (res.next()) {
				Integer id = res.getInt("ID");
				String Hname = res.getString("Name");
				String hash = res.getString("Hash");
				Hash newHash = new Hash(id, Hname, hash);
				result.add(newHash);
			}
			res.close();
			stm.close();
			con.close();
			return result;
		} catch (SQLException | ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public static Boolean unchangedHash(Hash hash) throws SQLException {
		Boolean res = false;
		Collection<Hash> col = getHash(hash.getName());
		List<Hash> result = col.stream()
				.filter(x -> hash.getHash().equals(x.getHash()))
				.collect(Collectors.toList());
		if (!result.isEmpty())
			res = true;
		return res;
	}
}
