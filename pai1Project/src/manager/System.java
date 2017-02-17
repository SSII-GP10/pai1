package manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import domain.Hash;

public class System {
    private static System instance;
    
    private String algorithm;
    private int timeCheck;
    private File incidentFile;
    private File kpiFile;

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
        	calculateHashes(filesAr);
        }
    }
    
    private void calculateHashes(String[] files) throws NoSuchAlgorithmException, IOException{
    	for(int i=0; i<files.length; i++){
    		File file = new File(files[i]);
    		String checkSum = Utilities.getFileChecksum(algorithm, file);
    		Hash hash = new Hash(1, files[i], checkSum);
    		HashManager.getInstance().addHash(hash);
    	}
    }
    
}