package sender;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MAC {
	
	SecretKey hmacKey;
	String MACString;

	public void init() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA512"); 
		keygen.init(256);
		hmacKey = keygen.generateKey();
	}
	
	public String exportMACKey() {
		MACString = encode(hmacKey.getEncoded());
		return MACString;
	}
	
	
	public String computeMAC(String message) throws NoSuchAlgorithmException, InvalidKeyException {
		// TODO Auto-generated method stub
		Mac mac = Mac.getInstance("HmacSHA512");
		mac.init(new SecretKeySpec(hmacKey.getEncoded(), "HmacSHA512")); // Initialize Mac object with symmetric key(K), same as with sender
		mac.update(message.getBytes());// add message data (M) to Mac object to compute Mac. 
		byte[] senderMac = mac.doFinal(); // Compute MAC
		MACString = encode(senderMac);
		System.out.println("MAC : " + encode(senderMac));
		return MACString;
	}
	
	private byte[] decode(String data) {
		return Base64.getDecoder().decode(data);
	}
	
	private String encode(byte[] data) { //helps convert data into a strings
        return Base64.getEncoder().encodeToString(data);
    }
	
	public void initFromStrings(String key) { //takes the string generated form of secretKey and IV from senders & converts them to their actual datatype
		hmacKey = new SecretKeySpec(decode(key),"HmacSHA512");
		System.out.println("hmac Key loaded: " + hmacKey);
	}
	
	
}
