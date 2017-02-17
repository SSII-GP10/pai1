package manager;

import java.util.Collection;

import domain.Hash;
import persistence.HashRepository;

public class HashManager {
	public static HashManager instance;
	
	private HashManager(){
	}
	
	private static HashManager getInstance(){
		if(instance == null){
			instance = new HashManager();
		}
		return instance;
	}
	
	public void addHash(Hash hash) throws IllegalArgumentException {
		HashRepository.insertHash(hash);
	}
	
	public Collection<Hash> getAllHashes(){
		return HashRepository.getAllHashes();
	}
	
}
