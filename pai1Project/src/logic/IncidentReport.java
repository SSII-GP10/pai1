package logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import domain.Hash;
import domain.Incident;

public class IncidentReport extends TimerTask {
	private static Core core;
	private static IncidentManager incidentManager;

	private BufferedWriter out;

	public IncidentReport() {
		this.core = Core.getInstance();
		this.incidentManager = IncidentManager.getInstance();
	}

	@Override
	public void run() {
		try {
			Collection<Incident> incidents = incidentManager.getAllIncidents();
			Iterator<Incident> it = incidents.iterator();
			open();
			while (it.hasNext()) {
				Incident incident = it.next();
				writeLine(incident.toString());
			}
			close();
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error !",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void open() throws IOException {
		File fileOut = core.getIncidentFile();
		try {
			out = new BufferedWriter(new FileWriter(fileOut.getName()));
		} catch (IOException e) {
			throw new IOException("Cant create incident file");
		}
	}

	private void writeLine(String line) throws IOException {
		try {
			out.write(line);
			out.newLine();
		} catch (IOException e) {
			throw new IOException("Cant write incident file line");
		}
	}

	private void close() throws IOException {
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new IOException("Cant close incident file");
		}
	}

}
