package com.ewcms.sms;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 *
 * @author wu_zhijun
 */
public class Message {
	public static void main(String[] args) throws ApiException{
		//官网的URL http请求，正式环境地址
		String url = "http://gw.api.taobao.com/router/rest";
		//TOP分配给应用的AppKey
		String appkey = "23614523";
		//TOP分配给应用的密匙
		String secret = "d8c22fd98178b7eef911715cd75343bb";
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		//实例 化请求
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		//公共回传参数，在"消息返回”中会透传回该参数
		req.setExtend("123456");
		//(必写)短信类型，传入值请填写normal
		req.setSmsType("normal");
		//(必写)短信签名，传入的短信签名必须是在阿里大于"管理中心－短信签名管理”中的可用签名
		req.setSmsFreeSignName("吴智俊");
		//短信模板变量，传参数则("key”:"value")
		req.setSmsParamString("{\"number\":\"" + createRandomVcode() + "\"}");
		//(必写)短信接收号码。支持单个和多个手机号码，多个号码间用英文逗号隔开
		req.setRecNum("18970986887");
		//短信模板ID，传入的模板必须是在阿里大于"管理中心-短信模板管理"中的可用模板
		req.setSmsTemplateCode("SMS_43155245");
		//实例 化响应
		AlibabaAliqinFcSmsNumSendResponse res = client.execute(req);
		System.out.println(res.getBody());
	}
	
	public static String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
}
