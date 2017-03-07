package com.ewcms.webservice;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;
import org.junit.Test;

/**
 * Base64 ( SHA1 ( nonce + created + password ) )
 * @author wu_zhijun
 */
public class PasswordDigestTest {

//	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	
//	@Test
//	public void testBuildNonce(){
//		try {
//			java.security.SecureRandom random = java.security.SecureRandom.getInstance("SHA1PRNG");
//			random.setSeed(System.currentTimeMillis()); 
//			byte[] nonceBytes = new byte[16]; 
//			random.nextBytes(nonceBytes); 
//			String nonce = new String(org.apache.commons.codec.binary.Base64.encodeBase64(nonceBytes), "UTF-8");
//			System.out.println(nonce);
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testBuilderPasswrodDigest2(){
//		ByteBuffer buf = ByteBuffer.allocate(1000);
//		try {
//			buf.put(Base64.decode("MWRhM2MyZjI3YzZkYmRmNg=="));
//			buf.put("2017-03-06T17:16:09Z".getBytes("UTF-8"));
//			buf.put("123456".getBytes("UTF-8"));
//			byte[] toHash = new byte[buf.position()];
//			buf.rewind();
//			buf.get(toHash);
//			byte[] hash = DigestUtils.sha(toHash);
//			System.out.println("Generated password digest: " + Base64.encode(hash));
//		} catch (Base64DecodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	@Test
	public void testBuildPasswordDigest3(){
		ByteBuffer buf = ByteBuffer.allocate(1000);
		
		try {
			buf.put(org.apache.commons.codec.binary.Base64.decodeBase64("ZGM3MmY4MjRiYWZmMWZkYg=="));
			buf.put("2017-03-07T10:12:00.318Z".getBytes("UTF-8"));
			buf.put("123456".getBytes("UTF-8"));
			byte[] toHash = new byte[buf.position()];
			buf.rewind();
			buf.get(toHash);
			byte[] hash = DigestUtils.sha(toHash);
			System.out.println("Generated password digest: " + org.apache.commons.codec.binary.Base64.encodeBase64String(hash));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void testBuildPasswordDigest(){
//		MessageDigest sha1;
//	    String passwordDigest = null;
//		String created = "2017-03-06T09:26:43.209Z";
//		String password = "123456";
//		String nonce = "/PuxN3uaq7UCFi7Vndpz5Q==";
//		
//		try {
//			
////			java.security.SecureRandom random = java.security.SecureRandom.getInstance("SHA1PRNG");
////			random.setSeed(sdf.parse(created).getTime()); 
////			byte[] nonceBytes = new byte[16]; 
////			random.nextBytes(nonceBytes); 
////			nonce = new String(org.apache.commons.codec.binary.Base64.encodeBase64(nonceBytes), "UTF-8");
//
//			
//			sha1 = MessageDigest.getInstance("SHA-1");
//			
//			sha1.update(nonce.getBytes("UTF-8"));
//			sha1.update(created.getBytes("UTF-8"));
//			sha1.update(password.getBytes("UTF-8"));
//			
//			passwordDigest = new String(Base64.encode(sha1.digest()));
//			
//			sha1.reset();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		System.out.println("nonce : " + nonce);
//		System.out.println("created : " + created);
//		System.out.println("password digest : " + passwordDigest);
//	}
}
