package reciever;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoadMessage {
	
	String message;
	
		

	public String loadinMessage(String path) { //load public RSA key from sender so that we can encrypt the AES key 
		try { 
			message = new String(Files.readAllBytes(Paths.get(path))); 
		} catch (IOException e) { e.printStackTrace(); }

		System.out.println("Message Loaded: " + message);
		return message;
	}

}
