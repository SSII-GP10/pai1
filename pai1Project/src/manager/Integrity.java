package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import domain.Hash;

public class Integrity extends TimerTask{
	private Collection<File> files;
	private String algorithm;
	
	private static Core core;
	private static HashManager hashManager;
	private static IncidentManager incidentManager;
	
	public Integrity(){
		this.core = Core.getInstance();
		this.hashManager = HashManager.getInstance();
		this.incidentManager = IncidentManager.getInstance();
		this.files = core.getFiles();
		this.algorithm = core.getAlgorithm();
	}
	
	public static void startCheckIntegrity(){
    	ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    	int timeCheck = Core.getInstance().getTimeCheck();
    	timer.scheduleAtFixedRate(new Integrity(), 0, timeCheck, TimeUnit.MINUTES);
    }
	
	@Override
	public void run() {
		check();
	}
	
	private Collection<Hash> changedHashes() throws NoSuchAlgorithmException, FileNotFoundException, SQLException{
		Collection<Hash> changedHashes = new ArrayList<Hash>();
		Collection<Hash> hashes = hashManager.calculateHashes(files, algorithm);
		Iterator<Hash> it = hashes.iterator();
		while(it.hasNext()){
			Hash hash = it.next();
			boolean unChanged = hashManager.unChangedHash(hash);
			if(!unChanged){
				changedHashes.add(hash);
			}
		}
		return changedHashes;
	}
	
	private void generateIncidents(Collection<Hash> hashes) {
		Iterator<Hash> it = hashes.iterator();
		while(it.hasNext()){
			Hash hash = it.next();
		}
	}
	
	private void check(){
		try {
			Collection<Hash> changedHashes = changedHashes();
			if(!changedHashes.isEmpty()){
				core.addConfigurationHashes();
				generateIncidents(changedHashes);
				JOptionPane.showMessageDialog(null, "Found " + changedHashes.size() + " files no integrity !",
					    "Warning !", JOptionPane.WARNING_MESSAGE);
			}
		} catch (NoSuchAlgorithmException | FileNotFoundException
				| SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
				    "Error !", JOptionPane.ERROR_MESSAGE);
		}
	}
}
