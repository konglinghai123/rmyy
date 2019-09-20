package com.ewcms.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsTest {

	public static void main(String[] args) {
		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		IClientProfile profile = DefaultProfile.getProfile("dysmsapi.aliyuncs.com", "LTAI4FuKFfqgqrQEwHRg9g7R", "9ifJfdb6tgTXfXrhJE0qJ8syOTzQFa");
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", "cn-hangzhou");
		request.putQueryParameter("PhoneNumbers", "18970986887");
		request.putQueryParameter("SignName", "赣凯科技");
		request.putQueryParameter("TemplateCode", "SMS_174275095");
		request.putQueryParameter("TemplateParam", "{\"name\":\"黄水金\",\"date\":\"2019-10-10\",\"organizationName\":\"江西省人民医院骨质疏松门诊\",\"telephone\":\"13361653980\"}");

		try {
			CommonResponse response = client.getCommonResponse(request);
			System.out.println(response.getData());
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
