package reciever;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSA {
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private String publicKeyString;
	private String privateKeyString;
	
	public void init() throws NoSuchAlgorithmException, IOException {
		//generate public and private RSA Keys
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(1024);
		KeyPair pair = generator.generateKeyPair();
		privateKey = pair.getPrivate();
		publicKey = pair.getPublic();
		
		//Save Keys in String format
		publicKeyString = encode(publicKey.getEncoded());
		System.out.println("Public key: " + encode(publicKey.getEncoded()));
		privateKeyString = encode(privateKey.getEncoded());
		savePrivateKey(privateKeyString); //saves private key **
		System.out.println("Private key: " + encode(privateKey.getEncoded()));
		System.out.println("Public Key From Init: " + publicKey);
	}
	
	public void initFromString() throws NoSuchAlgorithmException { //converts rsa keys back to their actual datatypes
		try {
			X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(publicKeyString));
			PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(privateKeyString));
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpecPublic);
			privateKey = keyFactory.generatePrivate(keySpecPrivate);
//			System.out.println(encode(publicKey.getEncoded()));
		}catch(Exception ignored) {}
	}
	
	private String encode(byte[] data) { //helps convert data into a strings
        return Base64.getEncoder().encodeToString(data);
    }
	
	public void exportPublicRSAKey(String path) throws IOException {
//		System.out.println("Public Key Written to File: " + publicKeyString);
		File output = new File(path);
		FileWriter writer = new FileWriter(output);
		writer.write(publicKeyString);
		writer.flush();
		writer.close();
	}
	
	
	
	public void savePrivateKey(String privateKey) throws IOException {
		System.out.println("Private Key Written to File: " + privateKey);
		File output = new File("/Users/ricardobaeza/Documents/privateRSAKey.txt"); //**
		FileWriter writer = new FileWriter(output);
		writer.write(privateKey);
		writer.flush();
		writer.close();
	}
	
	public String loadPrivateKey() { //load public RSA key from sender so that we can encrypt the AES key 
		try { 
			privateKeyString = new String(Files.readAllBytes(Paths.get("/Users/ricardobaeza/Documents/privateRSAKey.txt"))); 
		} catch (IOException e) { e.printStackTrace(); }

		System.out.println("String Private Key Loaded: " + privateKeyString);
		return privateKeyString;
	}
	
	public String loadPublicteKey() { //load public RSA key from sender so that we can encrypt the AES key 
		try { 
			publicKeyString = new String(Files.readAllBytes(Paths.get("/Users/ricardobaeza/Documents/publicRSAKey.txt"))); 
		} catch (IOException e) { e.printStackTrace(); }

		System.out.println("String Public Key Loaded: " + publicKeyString);
		return publicKeyString;
	}
	
	
	
	public String decrypt(String encryptedMessage) throws Exception{ //decrypt will be used to decrypt encrypted aes key with rsa
		byte[] encryptedBytes = decode(encryptedMessage);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
		return new String(decryptedMessage,"UTF8");
	}
	
	private byte[] decode(String data) {
		return Base64.getDecoder().decode(data);
	}
	
	//Part 2 
	
	public String loadInPublicKey(String path) { //load public RSA key from sender so that we can encrypt the AES key 
		try { 
			publicKeyString = new String(Files.readAllBytes(Paths.get(path))); 
		} catch (IOException e) { e.printStackTrace(); }

		System.out.println("RSA Public Key: " + publicKeyString);
		return publicKeyString;

	}
	
	
	
	public String encrypt(String message) throws Exception{ //encrypts data with RSA
		byte[] messageToBytes = message.getBytes();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE , publicKey);
		byte[] encryptedBytes = cipher.doFinal(messageToBytes);
		return encode(encryptedBytes);
	}
	
	public void initFromString_() throws NoSuchAlgorithmException { //convert public RSA String key back to public key data type 
		try {
		X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(publicKeyString));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		publicKey = keyFactory.generatePublic(keySpecPublic);
		}catch(Exception ignored) {}
	}
	
}
