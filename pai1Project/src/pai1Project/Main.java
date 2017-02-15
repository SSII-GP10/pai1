package pai1Project;



public class Main {
	private static Hash other = new Hash(1,"src/main/hash1.bin","asd5fs5df5sdf5");
	private static Hash changed = new Hash(2,"n1","65465464");
	private static Hash unchanged = new Hash(2,"n1","654654564");

	public static void main(String[] args) {
		//Testing DB connection
		DBConnection.createDB();
		//let's populate the DB
		DBConnection.insertHash(2, "654654564", "n1");
		DBConnection.insertHash(other);
		System.out.println(DBConnection.getHash("n1"));
		System.out.println(DBConnection.getAllHashes());
		System.out.println("This should be true: "+DBConnection.unchangedHash(unchanged));
		System.out.println("This should be false: "+DBConnection.unchangedHash(changed));

	}
}
