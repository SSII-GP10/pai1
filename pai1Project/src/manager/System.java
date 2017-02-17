package manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class System {
    private static System instance;
    
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
    
    private System() throws FileNotFoundException, IOException, NullPointerException, NumberFormatException, NoSuchAlgorithmException{
        this.files = new ArrayList<>();
    	this.readConfiguration();
    }
    
    public static System getInstance() throws FileNotFoundException, IOException, NullPointerException, NumberFormatException, NoSuchAlgorithmException{
        if(instance == null){
            instance = new System();
        }
        return instance;
    }
    
    private void readConfiguration() throws FileNotFoundException, IOException, NullPointerException, NumberFormatException, NoSuchAlgorithmException{
        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
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
    }
    
    private void addFiles(String[] files){
    	for(int i=0; i<files.length; i++){
    		File file = new File(files[i]);
    		this.files.add(file);
    	}
    }
    
}