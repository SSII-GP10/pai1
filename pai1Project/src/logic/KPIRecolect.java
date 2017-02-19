package logic;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import domain.KPI;

public class KPIRecolect extends TimerTask{
	private static HashManager hashManager;
	private static KPIManager kpiManager;
	
	public KPIRecolect(){
		this.hashManager = HashManager.getInstance();
		this.kpiManager = KPIManager.getInstance();
	}
	
	@Override
	public void run() {
		try {
			int total = hashManager.getAllHashes().size();
			int noIntegrity = hashManager.changedHashes().size();
			int integrity = total-noIntegrity;
			
			double ratio = integrity/(total*1.);
			KPI kpi = new KPI(0, ratio, integrity, noIntegrity, new Date());
			kpiManager.addKPI(kpi);
		} catch (NoSuchAlgorithmException | FileNotFoundException
				| SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error !",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	
}
