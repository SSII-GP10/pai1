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
			startTask(core.getTimeCheck(), TimeUnit.MINUTES, new Integrity());
			startTask(3, TimeUnit.MINUTES, new IncidentReport());
			startTask(2, TimeUnit.MINUTES, new KPIRecolect());
			startTask(3, TimeUnit.MINUTES, new KPIReport());
		} catch (SQLException | FileNotFoundException
				| NoSuchAlgorithmException | NullPointerException e) {
			System.out.println(e.getMessage());
			//JOptionPane.showMessageDialog(null, e.getMessage(),
				    //"Error !", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	private static void startTask(int time, TimeUnit unit, TimerTask task){
		ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
	    timer.scheduleAtFixedRate(task, 0, time, unit);
	}
}
