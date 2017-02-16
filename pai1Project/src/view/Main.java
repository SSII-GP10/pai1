package view;

import persistence.DBConnection;

public class Main {
	public static void main(String[] args){
		//Testing DB connection
		DBConnection.createDB();
		//let's populate the DB
		HashRepository.insertHash(2, "654654564", "n1");
		HashRepository.insertHash(other);
		System.out.println(HashRepository.getHash("n1"));
		System.out.println(HashRepository.getAllHashes());
		System.out.println("This should be true: "+HashRepository.unchangedHash(unchanged));
		System.out.println("This should be false: "+HashRepository.unchangedHash(changed));
	}
}
