package manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import domain.Hash;

public class Core {
    private static Core instance;
    private HashManager hashManager;
    
    private Collection<File> files;
    private String algorithm;
    private int timeCheck;
    private File incidentFile;
    private File kpiFile;

    public Collection<File> getFiles(){
    	return files;
    }
    
    public void setFiles(Collection<File> files){
    	this.files = files;
    }
    
    public String getAlgorithm(){
    	return algorithm;
    }
    
    public void setAlgorithm(String algorithm){
    	this.algorithm = algorithm;
    }
    
    public int getTimeCheck() {
        return timeCheck;
    }

    public void setTimeCheck(int timeCheck) {
        this.timeCheck = timeCheck;
    }

    public File getIncidentFile() {
        return incidentFile;
    }

    public void setIncidentFile(File incidentFile) {
        this.incidentFile = incidentFile;
    }

    public File getKpiFile() {
        return kpiFile;
    }

    public void setKpiFile(File kpiFile) {
        this.kpiFile = kpiFile;
    }
    
    private Core(){
        this.files = new ArrayList<>();
        this.hashManager = HashManager.getInstance();
    }
    
    public static Core getInstance() {
        if(instance == null){
            instance = new Core();
        }
        return instance;
    }
    
    public void readConfiguration() throws FileNotFoundException{
    	try{
            InputStream input = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(input);
            String files = prop.getProperty("files");
            String[] filesAr = files.split(";");
            String algorithm = prop.getProperty("algorithm");
            String timeCheckStr = prop.getProperty("timeCheck");
            String incidentPath = prop.getProperty("incidentPath");
            String kpiPath = prop.getProperty("kpiPath");
            if(files == null || algorithm == null || timeCheckStr == null || incidentPath == null || kpiPath == null){
                throw new NullPointerException("Errores en el archivo de configuracion");
            }else{
                this.algorithm = algorithm;
                this.timeCheck = Integer.parseInt(timeCheckStr);
                this.incidentFile = new File(incidentPath);
                this.kpiFile = new File(kpiPath);
            	addFiles(filesAr);
            }
    	}catch (IOException e){
    		throw new FileNotFoundException("Error: El archivo de configuracion no existe.");
    	}
    }
    
    private void addFiles(String[] files){
    	for(int i=0; i<files.length; i++){
    		File file = new File(files[i].trim());
    		this.files.add(file);
    	}
    }
    
    public void addConfigurationHashes() throws SQLException, NoSuchAlgorithmException, FileNotFoundException {
    	hashManager.removeAllHashes();
    	Collection<Hash> hashes = hashManager.calculateHashes(files, algorithm);
    	Iterator<Hash> it = hashes.iterator();
    	while(it.hasNext()){
    		Hash hash = it.next();
    		hashManager.addHash(hash);
    	}
    }
    
}