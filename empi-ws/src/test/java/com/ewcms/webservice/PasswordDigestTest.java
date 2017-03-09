package com.ewcms.webservice;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.Base64;
import org.junit.Test;

import com.ewcms.webservice.util.WebServiceUtil;

/**
 * Base64(SHA1(nonce + created + password ))
 * 
 * @author wu_zhijun
 */
public class PasswordDigestTest {
	
	@Test
	public void testBuildPassword(){
		String nonce = "MjZjYjM2ZDdhOWQ5NDM3Yg==";
		String created = "2017-03-09T11:41:07.135Z";
		String password = "123456";
		
		try {
			byte[] b1 = nonce != null ? Base64.decode(nonce) : new byte[0];
            byte[] b2 = created != null ? created.getBytes(StandardCharsets.UTF_8) : new byte[0];
            byte[] b3 = password.getBytes(StandardCharsets.UTF_8);
            byte[] b4 = new byte[b1.length + b2.length + b3.length];
            int offset = 0;
            System.arraycopy(b1, 0, b4, offset, b1.length);
            offset += b1.length;

            System.arraycopy(b2, 0, b4, offset, b2.length);
            offset += b2.length;

            System.arraycopy(b3, 0, b4, offset, b3.length);
            
            byte[] digestBytes = WebServiceUtil.generateDigest(b4);
            
            System.out.println(Arrays.toString(digestBytes));
            System.out.println(Base64.encode(digestBytes));
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
		
	}
	

}
