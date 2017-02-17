package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;


import domain.Hash;
import domain.KPI;
import persistence.HashRepository;
import persistence.IncidentRepository;
import persistence.KPIRepository;

public class Test {
	private static Hash other = new Hash(1,"src/main/hash1.bin","asd5fs5df5sdf5");
	private static Hash changed = new Hash(2,"n1","65465464");
	private static Hash unchanged = new Hash(2,"n1","654654564");
	
	public static void testHashes(){
		HashRepository.insertHash("654654564", "n1");
		HashRepository.insertHash("654654564", "ASDFASF");
		HashRepository.insertHash(other);
		System.out.println(HashRepository.getHash("n1"));
		System.out.println(HashRepository.getAllHashes());
		System.out.println("This should be true: "+HashRepository.unchangedHash(unchanged));
		System.out.println("This should be false: "+HashRepository.unchangedHash(changed));
	}
	public static void testKPIS(){
		KPIRepository.insertKPI(0.2, 6, 2);
		List<KPI> res = KPIRepository.getAllKPI().stream().collect(Collectors.toList());
		System.out.println(res.get(0).getReportDate());
		System.out.println(KPIRepository.getAllKPI());
		KPIRepository.getKPIOfCurrentMonth().stream().forEach(x->System.out.println(x.getId()));		
	}

	public static void testIncidents() {
		Date now = new Date(System.currentTimeMillis());
		IncidentRepository.insertIncident("incidente en un archivo", now, 1);
		System.out.println(IncidentRepository.getAllIncidents());
		//System.out.println(IncidentRepository.getIncident(957));
	}
	
	public static void testCallingMethod(){
		Long t = 3000l;
		Timer timer =new java.util.Timer();
		
		timer.schedule( 
		        new java.util.TimerTask() {
		        	int i = 0;
		            @Override
		            public void run() {
		            	
		            	if(i==6)
		            		timer.cancel();
		            	i++;
		                System.out.println("Called every: "+ t/1000+" seconds");
		            }
		        }, 
		        0,
		        t
		);
		
	}
}
