package reciever;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoadEncryptedAESMessage {
	
	
	public String getEncrAESMess(String path) { //load public RSA key from sender so that we can encrypt the AES key 
		String encryptedAESKey = null;
		try { 
			encryptedAESKey = new String(Files.readAllBytes(Paths.get(path))); 
		} catch (IOException e) { e.printStackTrace(); }

//		System.out.println("Encrypted AES Message: " + encryptedAESKey);
		return encryptedAESKey;
	}

}
