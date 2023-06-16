package sender;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

//import reciever.LoadEncryptedAESMessage;
//import reciever.LoadSendersTrans;

public class Sender {
	//NOTE: LOOK AT REPORT TO SEE WHEN TO COMMENT AND UNCOMMENT PARTS 1, 2, and 3
	public static void main(String[] args) throws Exception {
		String messagesPath = "/Users/ricardobaeza/Documents/ComputerScience/EclipseProjects/Crypto Final Sender/src/sender/sendersMessage.txt";
		String rsaPublicKeyPath = "/Users/ricardobaeza/Documents/publicRSAKey.txt"; //**
		
		String transmittedDataPath = "/Users/ricardobaeza/Documents/sendersTransmission.txt";
		String transmittedEncrMessPath = "/Users/ricardobaeza/Documents/encryptedAESMessage.txt";
		
		String message; //message loaded from .txt file 
		String encryptedMessage; //message that sender will send to reciever
		String encryptedAESKey; //encrypted Aes key with rsa public key
		String aesKey;
		String IV;
		
		String MAC;
		String encryptedMAC;
		String MACKey;
		
		loadMessage load = new loadMessage();
		
		RSA rsa = new RSA();
		AES aes = new AES();
		MAC mac = new MAC();
		SendersTransmission sender = new SendersTransmission();
		
		//Part 1
		message = load.loadinMessage(messagesPath); //loads in message from .txt file (that will be sent to reciever)
		aes.init(); //generate AES Key

		//load senders message from txt file
		encryptedMessage = aes.encrypt(message);
		sender.writeToLine(encryptedMessage, transmittedDataPath, 1);
		
		aes.exportKeys();
		IV = aes.getIV();

//		//rsa
		rsa.loadInPublicKey(rsaPublicKeyPath);
		rsa.initFromString();
		aesKey = aes.getAESKey();
		encryptedAESKey = rsa.encrypt(aesKey); //encrypt AES key with reciever's RSA public key
		
		sender.writeToLine(encryptedMessage, transmittedDataPath, 1);
		
		sender.writeToLine(encryptedMessage, transmittedEncrMessPath, 1);
		
		sender.writeToLine(encryptedAESKey,transmittedDataPath, 2); //does not work on recievers end
	
		sender.writeToLine(IV, transmittedDataPath, 3);
		
		System.out.println("Encrypted AES Key: " + encryptedAESKey);
		System.out.println("Encrypted Message: " + encryptedMessage);
		
		mac.init();
		MACKey = mac.exportMACKey();
		System.out.println("MAC Key: " + MACKey);
		
		MAC = mac.computeMAC(message);
		encryptedMAC = rsa.encrypt(MAC); //encrypt MAC with RSA Public Key
		sender.writeToLine(encryptedMAC, transmittedDataPath, 4);
		sender.writeToLine(MACKey, transmittedDataPath, 5);
		System.out.println("Decrypted Message: " + aes.decrypt(encryptedMessage));
//		
//		//Part 2 where sender will send its own RSA keys
//		String sendersRsaPublicKeyPath = "/Users/ricardobaeza/Documents/sendersPublicRSAKey.txt"; //**
//		rsa.init();
//		rsa.exportPublicRSAKey(sendersRsaPublicKeyPath);
//		
		//Part 3
		
//		LoadEncryptedAESMessage loadEncrAESKMess = new LoadEncryptedAESMessage();
//		LoadRecieversTrans loadRecievers = new LoadRecieversTrans();
//		
//		String aesEncryptedMessage;
//		String encryptedAESMessagePath = "/Users/ricardobaeza/Documents/sendersAESEncryptedMessage.txt";
//		String sendersEncryptedMACWithRSA;
//		
//		String aesEncryptedKeyWithRSA;
//		String MacKey;
//		String sendersOGMessage;
//		String sendersMAC;
//		String recieversMac;
//		boolean authenticated = false;
//		
//		aesEncryptedMessage = loadRecievers.getLine(1);
//		aesEncryptedMessage = loadEncrAESKMess.getEncrAESMess(encryptedAESMessagePath);
//		System.out.println("AES Encrypted Message " + aesEncryptedMessage);
//		
//		
//		aesEncryptedKeyWithRSA = loadRecievers.getLine(2);
//		aesEncryptedKeyWithRSA = loadRecievers.getLine(1);
//		System.out.println("RSA Encrypted AES Key: " + aesEncryptedKeyWithRSA);
//		
//		IV = loadRecievers.getLine(3);
//		System.out.println("IV: " + IV);
//		
//		sendersEncryptedMACWithRSA = loadRecievers.getLine(6); //somehow Encrypted MAC is on line 6 
//		System.out.println("Senders RSA Encrypted MAC:" + sendersEncryptedMACWithRSA);
////		
//		MacKey = loadRecievers.getLine(10);
//		System.out.println("MAC Key:" + MacKey);
//		
//		rsa.loadPublicteKey();
//		rsa.loadPrivateKey();
//		rsa.initFromString_(); //converts public and private key strings back to key format datatype
//	
//		aesKey = rsa.decrypt(aesEncryptedKeyWithRSA);
//		aes.initFromStrings(aesKey, IV);
//		sendersOGMessage = aes.decrypt(aesEncryptedMessage);
////		
////		
//		sendersMAC = rsa.decrypt(sendersEncryptedMACWithRSA); //decrypted MAC Key with rsa private key
//		System.out.println("Senders MAC: " + sendersMAC);
////		
//		mac.initFromStrings(MacKey); //macKey is the symmetric key generated from sender
//	
//		recieversMac = mac.computeMAC(sendersOGMessage);
//		System.out.println("Recievers MAC: " + recieversMac);
//		
//		if (sendersMAC.equals(recieversMac)) {
//			authenticated = true;
//			System.out.println("The message sent by the sender was successfuly authenticated");
////			
//		}else {
//			authenticated = false;
//			System.out.println("This message cannot be authenticated. The sender should not be trusted.");
//		}
////		
//		System.out.println("Decrypted Message: " + aes.decrypt(aesEncryptedMessage));
//		System.out.println("Decrypted MAC:" + MacKey);

	
	}
	
}
