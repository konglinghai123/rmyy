package com.ewcms.webservice;

import javax.security.auth.callback.CallbackHandler;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class PasswordHandlerTest implements CallbackHandler {

	    public void handle(javax.security.auth.callback.Callback[] callbacks) {  
	        for (int i = 0; i < callbacks.length; i++) {  
	            WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];  
	            pc.setPassword("123456");  
	        }  
	    }  

	}