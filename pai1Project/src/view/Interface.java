package view;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import persistence.DBConnection;
import logic.Core;
import logic.IncidentReport;
import logic.Integrity;
import logic.KPIRecolect;
import logic.KPIReport;

public class Interface {
	public static void main() {
		try {
			System.out.println("Starting HID...");
			DBConnection.createDB();
			Core core = Core.getInstance();
			core.readConfiguration();
			core.addConfigurationHashes();
			ScheduledExecutorService timer = Executors.newScheduledThreadPool(4);
			timer.scheduleAtFixedRate(new Integrity(), 0, core.getTimeCheck(), TimeUnit.MINUTES);
			timer.scheduleAtFixedRate(new IncidentReport(), 0, 3, TimeUnit.MINUTES);
			timer.scheduleAtFixedRate(new KPIRecolect(), 0, 2, TimeUnit.MINUTES);
			timer.scheduleAtFixedRate(new KPIReport(), 0, 3, TimeUnit.MINUTES);
		} catch (SQLException | FileNotFoundException
				| NoSuchAlgorithmException | NullPointerException e) {
			System.out.println(e.getMessage());
			//JOptionPane.showMessageDialog(null, e.getMessage(),
				    //"Error !", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
