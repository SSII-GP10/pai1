package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import domain.Hash;
import domain.Incident;

public class Integrity extends TimerTask {
	private Collection<File> files;
	private String algorithm;

	private static Core core;
	private static HashManager hashManager;
	private static IncidentManager incidentManager;

	public Integrity() {
		this.core = Core.getInstance();
		this.hashManager = HashManager.getInstance();
		this.incidentManager = IncidentManager.getInstance();
		this.files = core.getFiles();
		this.algorithm = core.getAlgorithm();
	}

	@Override
	public void run() {
		try {
			Collection<Hash> changedHashes = hashManager.changedHashes();
			if (!changedHashes.isEmpty()) {
				incidentManager.generateIncidents(changedHashes);
				System.out.println("Found " + changedHashes.size()
						+ " files no integrity !");
				//JOptionPane.showMessageDialog(null,
						//"Found " + changedHashes.size()
								//+ " files no integrity !", "Warning !",
						//JOptionPane.WARNING_MESSAGE);
			}
		} catch (NoSuchAlgorithmException | SQLException
				| FileNotFoundException e) {
			System.out.println(e.getMessage());
			//JOptionPane.showMessageDialog(null, e.getMessage(), "Error !",
					//JOptionPane.ERROR_MESSAGE);
		}
	}

}
