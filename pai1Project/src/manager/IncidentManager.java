package manager;

import java.sql.SQLException;
import java.util.Collection;

import persistence.IncidentRepository;
import domain.Incident;

public class IncidentManager {
	public static IncidentManager instance;
	
	public static IncidentManager getInstance(){
		if(instance == null){
			instance = new IncidentManager();
		}
		return instance;
	}
	
	public void addIncident(Incident incident) throws SQLException {
		IncidentRepository.insertIncident(incident);
	}
	
	public Collection<Incident> getAllIncident() throws SQLException{
		return IncidentRepository.getAllIncidents();
	}
	
}
