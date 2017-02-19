package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilities {
	public static String getFileChecksum(String algorithm, File file)
			throws NoSuchAlgorithmException, FileNotFoundException {
		String checkSum = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			FileInputStream fis = new FileInputStream(file);
			byte[] byteArray = new byte[1024];
			int bytesCount = 0;
			while ((bytesCount = fis.read(byteArray)) != -1) {
				digest.update(byteArray, 0, bytesCount);
			}
			;
			fis.close();
			byte[] bytes = digest.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			checkSum = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new NoSuchAlgorithmException("Error: Algorithm not found.");
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Error: File " + file.getName()
					+ " not exist.");
		} catch (IOException e) {
		}
		return checkSum;
	}
}
