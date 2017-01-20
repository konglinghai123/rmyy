package org.apache.shiro.session.mgt.eis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.session.mgt.OnlineSession;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wu_zhijun
 */
public class OnlineSessionDAOTest {

	@Test
	public void testSerialize() throws IOException, ClassNotFoundException, DecoderException{
		OnlineSession session = new OnlineSession();
		
		session.setId(888);
		session.setHost("127.0.0.1");
		session.setTimeout(8);
		session.setUserId(888L);
		session.setAttribute("abc", "abc");
		session.setStatus(OnlineSession.OnlineStatus.force_logout);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(session);
		oos.close();
		bos.close();
		byte[] ojectBytes = bos.toByteArray();
		
		String objectHex = Hex.encodeHexString(ojectBytes);
		
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Hex.decodeHex(objectHex.toCharArray())));
		
		OnlineSession actualSession = (OnlineSession) ois.readObject();
		ois.close();
		
		Assert.assertEquals(session.getId(), actualSession.getId());
		Assert.assertEquals(session.getHost(), actualSession.getHost());
		Assert.assertEquals(session.getTimeout(), actualSession.getTimeout());
		Assert.assertEquals(session.getUserId(), actualSession.getUserId());
		Assert.assertEquals(session.getAttributes().get("abc"), actualSession.getAttributes().get("abc"));
		Assert.assertEquals(session.getStatus(), actualSession.getStatus());
		Assert.assertEquals(session.getSystemHost(), actualSession.getSystemHost());
	}
}
