package view;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import persistence.DBConnection;
import manager.Core;
import manager.Integrity;

public class Interface {
	public static void main() {
		try {
			DBConnection.createDB();
			Core core = Core.getInstance();
			core.readConfiguration();
			core.addConfigurationHashes();
			Integrity.startCheckIntegrity();
		} catch (SQLException | FileNotFoundException
				| NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
				    "Error !", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
