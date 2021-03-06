package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import domain.Hash;
import persistence.HashRepository;

public class HashManager {
	public static HashManager instance;

	public static HashManager getInstance() {
		if (instance == null) {
			instance = new HashManager();
		}
		return instance;
	}

	public void addHash(Hash hash) throws SQLException {
		HashRepository.insertHash(hash);
	}

	public boolean unChangedHash(Hash hash) throws SQLException {
		return HashRepository.unchangedHash(hash);
	}

	public Collection<Hash> getAllHashes() throws SQLException {
		return HashRepository.getAllHashes();
	}

	public void removeAllHashes() throws SQLException {
		HashRepository.createHashesTable();
	}

	public Collection<Hash> calculateHashes(Collection<File> files,
			String algorithm) throws NoSuchAlgorithmException,
			FileNotFoundException {
		Collection<Hash> hashes = new ArrayList<Hash>();
		Iterator<File> it = files.iterator();
		while (it.hasNext()) {
			File file = it.next();
			String hashFile = Utilities.getFileChecksum(algorithm, file);
			Hash hash = new Hash(0, file.getAbsolutePath(), hashFile);
			hashes.add(hash);
		}
		return hashes;
	}

	public Collection<Hash> changedHashes() throws NoSuchAlgorithmException,
			FileNotFoundException, SQLException {
		Core core = Core.getInstance();
		Collection<Hash> changedHashes = new ArrayList<Hash>();
		Collection<Hash> hashes = calculateHashes(core.getFiles(), core.getAlgorithm());
		Iterator<Hash> it = hashes.iterator();
		while (it.hasNext()) {
			Hash hash = it.next();
			boolean unChanged = unChangedHash(hash);
			if (!unChanged) {
				changedHashes.add(hash);
			}
		}
		return changedHashes;
	}
}
