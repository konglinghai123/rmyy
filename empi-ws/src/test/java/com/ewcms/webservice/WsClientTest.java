package com.ewcms.webservice;

import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.junit.Test;

import com.google.common.collect.Maps;

/**
 *
 * @author wu_zhijun
 */
public class WsClientTest {

	@Test
	public void testClient() {
		// 这个是用cxf 客户端访问cxf部署的webservice服务
		// 千万记住，访问cxf的webservice必须加上namespace ,否则通不过
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();

		org.apache.cxf.endpoint.Client client = dcf.createClient("http://localhost:8080/empi-ws/webservice/patient?wsdl");
		// url为调用webService的wsdl地址
		QName name = new QName("http://webservice.ewcms.com/patient", "compositePracticeNo");
		
		Map<String, Object> props = Maps.newHashMap();
		
		props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		// 密码类型 明文:PasswordText密文：PasswordDigest
		props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
		// 用户名
		props.put(WSHandlerConstants.USER, "wuzhijun");
		//将PasswordHandler 的类名传递给服务器，相当于传递了密码给服务器
        props.put(WSHandlerConstants.PW_CALLBACK_CLASS, PasswordHandlerTest.class.getName());
		
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(props);
		client.getOutInterceptors().add(wssOut);
		
		try {
			Object[] object = client.invoke(name, "00000000001", "v2.4", "T", "er7");
			System.out.println((String)object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
