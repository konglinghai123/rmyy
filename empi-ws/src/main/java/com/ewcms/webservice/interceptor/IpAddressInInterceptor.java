package com.ewcms.webservice.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.IpUtils;
import com.ewcms.empi.card.manage.entity.ClientEnroll;
import com.ewcms.empi.card.manage.service.ClientEnrollService;
import com.ewcms.webservice.util.WebServiceUtil;

/**
 *
 * @author wu_zhijun
 */
public class IpAddressInInterceptor extends AbstractPhaseInterceptor<Message> {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(IpAddressInInterceptor.class);
	
	@Autowired
	private ClientEnrollService clientEnrollService;
	
	public IpAddressInInterceptor(){
		super(Phase.RECEIVE);
	}
	
	public IpAddressInInterceptor(String phase){
		super(phase);
	}
	
	@Override
	public void handleMessage(Message message) throws Fault {
		
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		
		if (EmptyUtil.isNotNull(request)){
			String ipAddr = IpUtils.getIpAddr(request);
			WebServiceUtil.log("IpInterceptor", "IP address : {}", ipAddr);
			ClientEnroll clientEnroll = clientEnrollService.findByIp(ipAddr);
			if (EmptyUtil.isNull(clientEnroll)) {
				if (LOG.isDebugEnabled()){
					LOG.debug("IP address : " + ipAddr + " is denied");
				}
				
				throw new Fault(new IllegalAccessException("IP address : " + ipAddr + " is denied"));
			}
		}
	}
	
	@Override
	public void handleFault(Message message) {
		Exception exeption = message .getContent(Exception.class);  
        LOG.error(exeption.getMessage(), exeption); 
	}
}
