package com.ewcms.webservice.interceptor;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPException;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.xml.security.utils.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.IpUtils;
import com.ewcms.empi.card.manage.entity.ClientEnroll;
import com.ewcms.empi.card.manage.service.ClientEnrollService;
import com.ewcms.webservice.util.WebServiceUtil;

/**
 *  验证用户是否合法,加密为 Base64 ( SHA1 ( nonce + created + password ) )
 * 
 * @author wu_zhijun
 */
public class SignInInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(SignInInterceptor.class);

	@Autowired
	private ClientEnrollService clientEnrollService;
	
	public SignInInterceptor() {
		super(Phase.PRE_PROTOCOL);
	}
	
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>signInInterceptor start<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		try{
			HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
			ClientEnroll clientEnroll = clientEnrollService.findByIp(IpUtils.getIpAddr(request));
			if (clientEnroll == null){
				if (LOG.isDebugEnabled()){
					LOG.debug("IP not registered");
				}
	            SOAPException soapExc = new SOAPException("IP not registered");  
	            throw new Fault(soapExc);
			}
			
			if (clientEnroll.getAuth()){
				String userName = null, password = null, nonce = null, created = null;
				
				List<Header> headers = message.getHeaders();
				if (EmptyUtil.isCollectionEmpty(headers)){
	            	if (LOG.isDebugEnabled()){
						LOG.debug("Authentication failed!");
					}
					SOAPException soapEx = new SOAPException("Authentication failed!");  
		            throw new Fault(soapEx); 
				} 
				
				for (Header header : headers) {  
					if (header == null) continue;  
					
		            Element element = (Element) header.getObject();  
		            if (element == null) continue;  
		            
		            Node node = element.getFirstChild();  
		            if (node == null) continue;  
		            
		            if (element.getNodeName().equals("created")){
		            	created = node.getTextContent();
		            	/**  
				         * 保证与服务器时间前后相差不大于2分钟     当前时间 -请求时间  <= 2  
				        */  
				        long nd = 1000*24*60*60;//一天的毫秒数  
				        long nh = 1000*60*60;//一小时的毫秒数  
				        long nm = 1000*60;//一分钟的毫秒数  
				        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				        Date nowDate =  new Date();  
				        Date requestDate = df.parse(created);  
				        long ddff = nowDate.getTime() - requestDate.getTime();  
				        long min = ddff%nd%nh/nm;//计算差多少分钟  
				        if(min > 2){
				        	if (LOG.isDebugEnabled()){
				        		LOG.debug("Authentication timeout");
							}
				            SOAPException soapExc = new SOAPException("Authentication timeout");  
				            throw new Fault(soapExc);  
				        }  		            
				    } 
		            if (element.getNodeName().equals("userName")){
						userName = node.getTextContent();
						if (!userName.equals(clientEnroll.getUserName())){
							if (LOG.isDebugEnabled()){
								LOG.debug("User name mismatch");
							}
			                SOAPException soapExc = new SOAPException("User name mismatch");  
			                throw new Fault(soapExc);
						}
		            }
		            
		            if (element.getNodeName().equals("nonce")){
		            	nonce = node.getTextContent();
		            }
		            
		            if (element.getNodeName().equals("password")){
		            	password = node.getTextContent();
		            }
		            
				}

				if (EmptyUtil.isStringEmpty(nonce) || EmptyUtil.isStringEmpty(created)){
					if (LOG.isDebugEnabled()){
						LOG.debug("The security token could not be authenticated or authorized");
					}
	                SOAPException soapExc = new SOAPException("The security token could not be authenticated or authorized");  
	                throw new Fault(soapExc);
				} 
				
				String passDigest = doPasswordDigest(nonce, created, clientEnroll.getPassword());
	            
	            if (!passDigest.equals(password)){
	            	if (LOG.isDebugEnabled()){
						LOG.debug("The security token could not be authenticated or authorized");
					}
	                SOAPException soapExc = new SOAPException("The security token could not be authenticated or authorized");  
	                throw new Fault(soapExc);
	            }
	            
	            if (LOG.isDebugEnabled()){
	            	LOG.debug("userName : " + userName + "\r\n nonce : " + nonce + "\r\n created : " + created + "\r\n password : " + password);
	            	LOG.debug("Authentication success");
	            }
			} else {
				if (LOG.isDebugEnabled()){
	            	LOG.debug("No authentication required");
	            }
			}
		} catch (Exception e){
			throw new Fault(e);
		}
		
		LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>signInInterceptor end<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}

	private String doPasswordDigest(String nonce, String created, String password) {
        String passwdDigest = null;
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
            passwdDigest = Base64.encode(digestBytes);
        } catch (Exception e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e.getMessage(), e);
            }
        }
        return passwdDigest;
    }
}
