package com.royal;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author suporte
 */

public abstract class Cripto {
    

    public String decrypt(String toEncrypt) {
	throw new UnsupportedOperationException("não descriptografa essa instancia");
    }

    public String encrypt(String toEncrypt) {
	throw new UnsupportedOperationException("não criptografa essa instancia");
    }

    private static final MessageDigest messageDigest;
    
    static {
	try {
	    messageDigest = MessageDigest.getInstance("SHA-1");
	} catch (NoSuchAlgorithmException ex) {
	    throw new RuntimeException(ex);
	}
    }
    
    private static final String CIPHER_MODE = "AES/ECB/PKCS5Padding";
    private static final String KEY_MODE = "AES";

    public static class Encrypter extends Cripto {
	private final Cipher cipher;

	private Encrypter(Cipher cipher, SecretKeySpec keySpec) throws InvalidKeyException {
	    this.cipher = cipher;
	  
	    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
	}

	@Override
	public String encrypt(String toEncrypt) {
	    try {
		return Base64.getUrlEncoder().encodeToString(cipher.doFinal(toEncrypt.getBytes(StandardCharsets.UTF_8)));
	    } catch (IllegalBlockSizeException | BadPaddingException ex) {
		throw new RuntimeException(ex);
	    }
	}
	
	

	private static final Map<String, Encrypter> ENCRYPIES_CREATED = Collections.synchronizedMap(new HashMap<>());

	public static Encrypter of(String key) {
	    return ENCRYPIES_CREATED.getOrDefault(key, newInstance(key));
	}
	
	private static Encrypter newInstance(String key){
	    final SecretKeySpec keySpec = new SecretKeySpec(Arrays.copyOf(messageDigest.digest(key.getBytes(StandardCharsets.UTF_8)), 16), KEY_MODE);

		final Encrypter encrypt;
		
		try {
		    encrypt = new Encrypter(Cipher.getInstance(CIPHER_MODE), keySpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
		    throw new RuntimeException(ex);
		}
		
		ENCRYPIES_CREATED.put(key, encrypt);
		return encrypt;
	}
    }
    
    public static class Decrypter extends Cripto {

	private final Cipher cipher;

	private Decrypter(Cipher cipher, SecretKeySpec keySpec) throws InvalidKeyException {
	    this.cipher = cipher;
	    cipher.init(Cipher.DECRYPT_MODE, keySpec);
	}

	@Override
	public String decrypt(String toEncrypt) {
	    try {
		return new String(cipher.doFinal(Base64.getUrlDecoder().decode(toEncrypt)));
	    } catch (IllegalBlockSizeException | BadPaddingException ex) {
		throw new RuntimeException(ex);
	    }
	}
	

	private static final Map<String, Decrypter> DECRYPIES_CREATED = Collections.synchronizedMap(new HashMap<>());

	public static Decrypter of(String key) {
	    return DECRYPIES_CREATED.getOrDefault(key, newInstance(key));
	}
	
	private static Decrypter newInstance(String key){
	    final SecretKeySpec keySpec = new SecretKeySpec(Arrays.copyOf(messageDigest.digest(key.getBytes(StandardCharsets.UTF_8)), 16), KEY_MODE);

		final Decrypter decrypt;
		
		try {
		    decrypt = new Decrypter(Cipher.getInstance(CIPHER_MODE), keySpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
		    throw new RuntimeException(ex);
		}
		
		DECRYPIES_CREATED.put(key, decrypt);
		return decrypt;
	}
    }

}