package com.ewcms.hl7v2x.sendandreceive;

import java.io.IOException;
import java.util.Map;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;

/**
 *
 * @author wu_zhijun
 */
public class ExampleReceiverApplication implements ReceivingApplication{

	@Override
	public boolean canProcess(Message arg0) {
		return true;
	}

	@Override
	public Message processMessage(Message theMessage, Map<String, Object> theMetadata)
			throws ReceivingApplicationException, HL7Exception {
		 String encodedMessage = new DefaultHapiContext().getPipeParser().encode(theMessage);
		 System.out.println("Received message:\n" + encodedMessage + "\n\n");
		 try{
			 return theMessage.generateACK();
		 }catch (IOException e) {
			 throw new HL7Exception(e);
		 }
	}

}
