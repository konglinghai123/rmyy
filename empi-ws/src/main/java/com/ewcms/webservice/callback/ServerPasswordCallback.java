package com.ewcms.webservice.callback;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.ext.WSSecurityException.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.ClientEnroll;
import com.ewcms.empi.card.manage.service.ClientEnrollService;
import com.ewcms.webservice.util.WebServiceUtil;

/**
 *
 * @author wu_zhijun
 */
@Service("serverPasswordCallback")
public class ServerPasswordCallback implements CallbackHandler {

	@Autowired
	private ClientEnrollService clientEnrollService;
	
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		System.out.println("server:callbacks.length is " + callbacks.length);
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback wpc = (WSPasswordCallback) callbacks[i];
			String userName = wpc.getIdentifier();
			
			ClientEnroll clientEnroll = clientEnrollService.findByIpAndUserName(WebServiceUtil.getClientIpCxf(), userName);
			
			if (EmptyUtil.isNull(clientEnroll))
				try {
					throw new WSSecurityException(ErrorCode.FAILURE, "No Permission!");
				} catch (WSSecurityException e) {
				}
			
			wpc.setPassword(clientEnroll.getPassword());
		}
	}
}
