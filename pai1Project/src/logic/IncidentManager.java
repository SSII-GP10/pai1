package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.stream.Collectors;

import persistence.IncidentRepository;
import domain.Hash;
import domain.Incident;
import domain.KPI;

public class IncidentManager {
	public static IncidentManager instance;

	public static IncidentManager getInstance() {
		if (instance == null) {
			instance = new IncidentManager();
		}
		return instance;
	}

	public void addIncident(Incident incident) throws SQLException {
		IncidentRepository.insertIncident(incident);
	}
	
	public Collection<Incident> getIncidentActualDay() throws SQLException{
		Collection<Incident> result = new ArrayList<Incident>();
		Iterator<Incident> it = getAllIncidents().iterator();
		Date actualDate = new Date();
		while(it.hasNext()){
			Incident incident = it.next();
			Date dateIncident = incident.getDate();
			if(dateIncident == actualDate){
				result.add(incident);
			}
		}
		return result;
	}

	public Collection<Incident> getAllIncidents() throws SQLException {
		return IncidentRepository.getAllIncidents();
	}

	public void generateIncidents(Collection<Hash> hashes) throws SQLException {
		try {
			Iterator<Hash> it = hashes.iterator();
			while (it.hasNext()) {
				Hash hash = it.next();
				Incident incident = new Incident(0, "Raped Integrity",
						new Date(), hash.getName());
				addIncident(incident);
			}
		} catch (SQLException e) {
			throw e;
		}
	}
}
