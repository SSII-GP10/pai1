package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import domain.Incident;
import domain.KPI;
import persistence.IncidentRepository;
import persistence.KPIRepository;

public class KPIManager {
	private static KPIManager instance;

	public static KPIManager getInstance() {
		if (instance == null) {
			instance = new KPIManager();
		}
		return instance;
	}

	public void addKPI(KPI kpi) throws SQLException {
		KPIRepository.insertKPI(kpi);
	}
	
	public Collection<KPI> getKpiActualMonth() throws SQLException{
		return KPIRepository.getKPIOfCurrentMonth();
	}

	public Collection<KPI> getAllKpi() throws SQLException {
		return KPIRepository.getAllKPI();
	}

}
