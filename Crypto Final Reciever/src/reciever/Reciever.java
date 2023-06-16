package reciever;

//import sender.SendersTransmission;

public class Reciever {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		//NOTE: LOOK AT REPORT TO SEE WHEN TO COMMENT AND UNCOMMENT PARTS 1, 2, and 3
		System.out.println("RECIEVER");
		String secretKey; String IV; String encryptedKey; String encryptedMessage;
		String rsaPublicKeyPath = "/Users/ricardobaeza/Documents/publicRSAKey.txt"; //**
		String encryptedAESMessagePath = "/Users/ricardobaeza/Documents/encryptedAESMessage.txt";
		String aesEncryptedKeyWithRSA;
		String aesKey;
		String sendersEncryptedMACWithRSA;
		String MacKey;
		String sendersOGMessage;
		String sendersMAC;
		String recieversMac;
		boolean authenticated = false;
		
		
		String aesEncryptedMessage;
		
		RSA rsa = new RSA();
		AES aes = new AES();
		MAC mac = new MAC();
		LoadSendersTrans loadSenders = new LoadSendersTrans();
		LoadEncryptedAESMessage loadEncrAESKMess = new LoadEncryptedAESMessage();
		
//		//Part 1
//		rsa.init();
//		rsa.exportPublicRSAKey(rsaPublicKeyPath);
		
////		Part 2
		
		aesEncryptedMessage = loadEncrAESKMess.getEncrAESMess(encryptedAESMessagePath);
		System.out.println("AES Encrypted Message: " + aesEncryptedMessage);
		
		aesEncryptedKeyWithRSA = loadSenders.getLine(2);
		aesEncryptedKeyWithRSA = loadSenders.getLine(1);
		System.out.println("RSA Encrypted AES Key: " + aesEncryptedKeyWithRSA);
		
//		aesEncryptedKeyWithRSA = loadSenders.getLine(2); //this method does not load in encrypted AES Key
//		aesEncryptedKeyWithRSA = loadEncrAESKey.getEncrAESKey(encryptedAESKeyPath);
		
		IV = loadSenders.getLine(3);
		System.out.println("IV: " + IV);
		
		sendersEncryptedMACWithRSA = loadSenders.getLine(6); //somehow Encrypted MAC is on line 6 of sendersTrans.txt file
		System.out.println("Senders RSA Encrypted MAC:" + sendersEncryptedMACWithRSA);
		
		MacKey = loadSenders.getLine(10);
		System.out.println("MAC Key:" + MacKey);
		
		rsa.loadPublicteKey();
		rsa.loadPrivateKey();
		rsa.initFromString();
		
		aesKey = rsa.decrypt(aesEncryptedKeyWithRSA);
		aes.initFromStrings(aesKey, IV);
		sendersOGMessage = aes.decrypt(aesEncryptedMessage);
		
		sendersMAC = rsa.decrypt(sendersEncryptedMACWithRSA); //decrypted MAC Key with rsa private key
		System.out.println("Senders MAC: " + sendersMAC);
		
		mac.initFromStrings(MacKey); //macKey is the symmetric key generated from sender
//		
		recieversMac = mac.computeMAC(sendersOGMessage);
		System.out.println("Recievers MAC: " + recieversMac);
		
		if (sendersMAC.equals(recieversMac)) {
			authenticated = true;
			System.out.println("The message sent by the sender was successfuly authenticated");
			
		}else {
			authenticated = false;
			System.out.println("This message cannot be authenticated. The sender should not be trusted.");
		}
		
		System.out.println("Decrypted Message: " + aes.decrypt(aesEncryptedMessage));
		System.out.println("Decrypted MAC:" + MacKey);
		
		//Part 3 Reciever to Sender (message)
//		String message;
//		String messagesPath = "/Users/ricardobaeza/Documents/ComputerScience/EclipseProjects/Crypto Final Reciever/src/reciever/recieversMessageToSender.txt";
//		String sendersRsaPublicKeyPath = "/Users/ricardobaeza/Documents/sendersPublicRSAKey.txt";
//		String transmittedDataPath = "/Users/ricardobaeza/Documents/recieversTransmission.txt";
//		String encryptedAESKey;
//		String MACKey;
//		String MAC;
//		String encryptedMAC;
//		String AESencryptedMessagePath = "/Users/ricardobaeza/Documents/sendersAESEncryptedMessage.txt";
//		
//		LoadMessage load = new LoadMessage();
//		RecieversTransmission reciever = new RecieversTransmission();
//		
//		message = load.loadinMessage(messagesPath); //loads in message from .txt file (that will be sent to sender)
//		aes.init(); //generate AES Key
//		
//		encryptedMessage = aes.encrypt(message);
//		
//		reciever.writeToLine(encryptedMessage, AESencryptedMessagePath, 1);
//		reciever.writeToLine(encryptedMessage, transmittedDataPath, 1);
//		System.out.println("AES Encrypted Message: " + encryptedMessage);
//		
//		aes.exportKeys();
//		IV = aes.getIV();
//		
//		//rsa
//		rsa.loadInPublicKey(sendersRsaPublicKeyPath);
//		rsa.initFromString_();
//		aesKey = aes.getAESKey();
//		encryptedAESKey = rsa.encrypt(aesKey); //encrypt AES key with reciever's RSA public key
//
//		
//		reciever.writeToLine(encryptedAESKey,transmittedDataPath, 2);
//		
//		reciever.writeToLine(IV, transmittedDataPath, 3);
////
//		System.out.println("Encrypted AES Key: " + encryptedAESKey);
//		System.out.println("Encrypted Message: " + encryptedMessage);
//
//		mac.init();
//		MACKey = mac.exportMACKey();
//		System.out.println("MAC Key: " + MACKey);
//		
//		MAC = mac.computeMAC(message);
//		encryptedMAC = rsa.encrypt(MAC); //encrypt MAC with RSA Public Key
//		reciever.writeToLine(encryptedMAC, transmittedDataPath, 4);
//		reciever.writeToLine(MACKey, transmittedDataPath, 5);
//		System.out.println("Decrypted Message: " + aes.decrypt(encryptedMessage));
		
		
	}

}
