package com.ewcms.web.servlet;

import java.io.IOException;

import org.junit.Test;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.hoh.api.DecodeException;
import ca.uhn.hl7v2.hoh.api.EncodeException;
import ca.uhn.hl7v2.hoh.api.IAuthorizationClientCallback;
import ca.uhn.hl7v2.hoh.api.IReceivable;
import ca.uhn.hl7v2.hoh.api.ISendable;
import ca.uhn.hl7v2.hoh.api.MessageMetadataKeys;
import ca.uhn.hl7v2.hoh.auth.SingleCredentialClientCallback;
import ca.uhn.hl7v2.hoh.hapi.api.MessageSendable;
import ca.uhn.hl7v2.hoh.hapi.client.HohClientSimple;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v251.message.ADT_A01;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;

/**
 *
 * @author wu_zhijun
 */
public class Hl7v2ServletTest {

	@Test
	public void testHL7V2Servlet() {
		/*
		 * http://localhost:8080/empi-web/hl7v2
		 */
		String host = "localhost";
		int port = 8080;
		String uri = "/empi-web/hl7v2";

		// 建立解析器
		Parser parser = PipeParser.getInstanceWithNoValidation();

		// 建立客户端
		HohClientSimple client = new HohClientSimple(host, port, uri, parser);

		// Optionally, if credentials should be sent, they
		// can be provided using a credential callback
		IAuthorizationClientCallback authCalback = new SingleCredentialClientCallback("ausername", "somepassword");
		client.setAuthorizationCallback(authCalback);

		// The ISendable defines the object that provides the actual
		// message to send
		ADT_A01 adt = new ADT_A01();
		try {
			adt.initQuickstart("ADT", "A01", "T");
			// .. set other values on the message ..
	
			// The MessageSendable provides the message to send
			ISendable<Message> sendable = new MessageSendable(adt);

			// sendAndReceive actually sends the message
			IReceivable<Message> receivable = client.sendAndReceiveMessage(sendable);

			// receivavle.getRawMessage() provides the response
			Message message = receivable.getMessage();
			System.out.println("Response was:\n" + message.encode());

			// IReceivable also stores metadata about the message
			String remoteHostIp = (String) receivable.getMetadata().get(MessageMetadataKeys.REMOTE_HOST_ADDRESS);
			System.out.println("From:\n" + remoteHostIp);

			/*
			 * Note that the client may be reused as many times as you like, by
			 * calling sendAndReceiveMessage repeatedly
			 */

		} catch (DecodeException e) {
			// Thrown if the response can't be read
			e.printStackTrace();
		} catch (IOException e) {
			// Thrown if communication fails
			e.printStackTrace();
		} catch (EncodeException e) {
			// Thrown if the message can't be encoded (generally a programming
			// bug)
			e.printStackTrace();
		} catch (HL7Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
