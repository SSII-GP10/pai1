package view;

import persistence.DBConnection;

public class Main {
	public static void main(String[] args){
		DBConnection.createDB();
		Test.testHashes();
		Test.testKPIS();
		Test.testIncidents();
	}
}
