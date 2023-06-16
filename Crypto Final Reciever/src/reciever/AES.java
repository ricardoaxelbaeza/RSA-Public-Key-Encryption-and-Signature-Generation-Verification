package reciever;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	private SecretKey secretKey;
    private final int keySize = 128;
    private final int dataLength = 128;
    private Cipher encryptionCipher;
    private byte[] IV;
	
    //AES reciever code (1) 
	public String decrypt(String encryptedData) throws Exception { //decrypts AES encrypted message 
        byte[] dataInBytes = decode(encryptedData);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(dataLength, IV);
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(dataInBytes);
        return new String(decryptedBytes);
    }
	
	public void initFromStrings(String key, String IV) { //takes the string generated form of secretKey and IV from senders & converts them to their actual datatype
		secretKey = new SecretKeySpec(decode(key),"AES");
//		System.out.println("secretKey loaded: " + secretKey);
		this.IV = decode(IV);
//		System.out.println("IV loaded: " + IV);
	}
	
	private String encode(byte[] data) { //helps convert data into a strings
        return Base64.getEncoder().encodeToString(data);
    }
	
	private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
	
	//end of reciever code (1)
	
	//Part 2
	private String secretKeyString; 
	private String IVstring;
	
	public void init() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES"); //generate AES key
		keyGenerator.init(keySize);
		secretKey = keyGenerator.generateKey(); //set secretKey
	}
	
	public void exportKeys() { //exports secretKey and IV to strings 
		secretKeyString = encode(secretKey.getEncoded());
		System.out.println("Secret Key: " + secretKeyString);
		IVstring = encode(IV);
		System.out.println("IV: " + IVstring);
	}
	
	public String getIV() { //returns IV as a string so that we can store it in the transmission file that the sender will recieve
		return IVstring;
	}
	
	 public String getAESKey() {
		 return secretKeyString;
	 }
	 
	 public String encrypt(String data) throws Exception { //AES encryption
	        byte[] dataInBytes = data.getBytes();
	        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
	        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
	        IV = encryptionCipher.getIV();
	        byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
	        return encode(encryptedBytes);
	    }

}
