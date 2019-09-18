package com.ewcms.sms;

import org.springframework.beans.factory.annotation.Value;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsUtil {
	@Value("${domain}")
	private String domain;
	@Value("${RegionId}")
	private String RegionId;
	@Value("${accessKeyId}")
	private String accessKeyId;
	@Value("${accessKeySecret}")
	private String accessKeySecret;
	@Value("${signName}")
	private String signName;
	@Value("${templateCode}")
	private String templateCode;
	@Value("${templateCode1}")
	private String templateCode1;

	public String sendSms(String phone, String param, Boolean isTelephone) {
		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		IClientProfile profile = DefaultProfile.getProfile(RegionId, accessKeyId, accessKeySecret);
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain(domain);
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", RegionId);
		request.putQueryParameter("PhoneNumbers", phone);
		request.putQueryParameter("SignName", signName);
		if (isTelephone)
			request.putQueryParameter("TemplateCode", templateCode);
		else
			request.putQueryParameter("TemplateCode", templateCode1);
		
		request.putQueryParameter("TemplateParam", param);
		
		try {
			CommonResponse response = client.getCommonResponse(request);
			return response.getData();
		} catch (ServerException e) {
			e.printStackTrace();
			return null;
		} catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
}
