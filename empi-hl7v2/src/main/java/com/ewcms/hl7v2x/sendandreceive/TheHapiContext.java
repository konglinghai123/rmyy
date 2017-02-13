package com.ewcms.hl7v2x.sendandreceive;

import java.util.concurrent.Executors;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.HL7Service;
import ca.uhn.hl7v2.validation.builder.support.DefaultValidationBuilder;
import ca.uhn.hl7v2.validation.builder.support.NoValidationBuilder;

/**
 *
 * @author wu_zhijun
 */
public class TheHapiContext {

	public static void main(String[] args){
		HapiContext ctx = new DefaultHapiContext();
		
		ctx.setValidationRuleBuilder(new NoValidationBuilder());
		
		HL7Service server1 = ctx.newServer(8080, false);
		HL7Service server2 = ctx.newServer(8081, false);
		
		HapiContext ctx1 = new DefaultHapiContext();
		ctx1.setValidationRuleBuilder(new NoValidationBuilder());
		server1 = ctx1.newServer(8080, false);
		
		HapiContext ctx2 = new DefaultHapiContext();
		ctx2.setValidationRuleBuilder(new DefaultValidationBuilder());
		server2 = ctx2.newServer(8081, false);
		
		ctx = new DefaultHapiContext();
		ctx.setExecutorService(Executors.newCachedThreadPool());
		
		
	}
}
