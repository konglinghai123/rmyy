package com.ewcms.hl7v2.message;

import java.io.IOException;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.hl7v2.HL7Constants;
import com.ewcms.hl7v2.MessageTriggerEvent;
import com.ewcms.hl7v2.segment.AL1Util;
import com.ewcms.hl7v2.segment.MSAUtil;
import com.ewcms.hl7v2.segment.MSHUtil;
import com.ewcms.hl7v2.segment.NK1Util;
import com.ewcms.hl7v2.segment.PIDUtil;
import com.ewcms.hl7v2.segment.PV1Util;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.AbstractSegment;
import ca.uhn.hl7v2.parser.Parser;

/**
 * 回复ADR消息(QRY消息请求)
 * 
 * @author wu_zhijun
 */
public class ADRUtil {

	private static HapiContext hapiContext = new DefaultHapiContext();
	private static String messageCode = MessageTriggerEvent.A19.getCode();
	private static String messageTriggerEvent = MessageTriggerEvent.A19.getTriggerEvent();

	/**
	 * 对象转化成ADR消息
	 * 
	 * @param patientBaseInfo 患者基本信息对象
	 * @param practiceNo 患者卡号
	 * @param patientIdLen 患者唯一索引号长度
	 * @param processingId 消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)
	 * @param version 版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param style HL7使用样式(xml或er7)
	 * @param receivingApplication 接收应用名称
	 * @return ADR消息
	 */
	public static String encode(PatientBaseInfo patientBaseInfo, String practiceNo, Integer patientIdLen, String processingId, String version, String style, String receivingApplication) {
		Parser parser = hapiContext.getPipeParser();
		if ("xml".equals(style.toLowerCase())) {
			parser = hapiContext.getXMLParser();
		}

		AbstractMessage adr = null;

		AbstractSegment msh = null;
		AbstractSegment msa = null;
		AbstractSegment pid = null;
		AbstractSegment nk1 = null;
		AbstractSegment pv1 = null;
		AbstractSegment al1 = null;

		String result = "";
		try{
			if (("2.1").equals(version) || ("v2.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v21.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				pv1 = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
			} else if (("2.2").equals(version) || ("v2.2").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v22.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			} else if (("2.3").equals(version) || ("v2.3").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v23.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			} else if (("2.3.1").equals(version) || ("v2.3.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v231.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2().getAL1();
			} else if (("2.4").equals(version) || ("v2.4").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v24.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			} else if (("2.5").equals(version) || ("v2.5").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v25.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			} else if (("2.5.1").equals(version) || ("v2.5.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v251.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			} else if (("2.6").equals(version) || ("v2.6").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v26.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getMSA();
				pid = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getQUERY_RESPONSE().getPID();
				nk1 = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getQUERY_RESPONSE().getNK1();
				pv1 = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getQUERY_RESPONSE().getPV1();
				al1 = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getQUERY_RESPONSE().getAL1();
			}
			
				
			if (EmptyUtil.isNotNull(patientBaseInfo)){
				if (EmptyUtil.isNotNull(msh)) {
					MSHUtil mshUtil = new MSHUtil(HL7Constants.SENDING_APPLICATION, "");
					mshUtil.setMsh(msh);
				}
				
				if (EmptyUtil.isNotNull(msa)) {
					MSAUtil masUtil = new MSAUtil(AcknowledgmentCode.AA.name(), "查询成功");
					masUtil.setMsa(msa);
				}
				
				if (EmptyUtil.isNotNull(pid)) {
					PIDUtil pidUtil = new PIDUtil(patientBaseInfo, practiceNo, patientIdLen);
					pidUtil.setPid(pid);
				}
				if (EmptyUtil.isNotNull(nk1)) {
					NK1Util nk1Util = new NK1Util(patientBaseInfo.getContactName(),
							patientBaseInfo.getContactRelation(),
							patientBaseInfo.getContactAddress(),
							patientBaseInfo.getContactTelephone());
					nk1Util.setNk1(nk1);
				}
				if (EmptyUtil.isNotNull(pv1)){
					PV1Util pv1Util = new PV1Util(patientBaseInfo.getPatientType());
					pv1Util.setPv1(pv1);
				}
				if (EmptyUtil.isNotNull(al1)) {
					AL1Util al1Util = new AL1Util(patientBaseInfo.getAllergyHistory());
					al1Util.setAl1(al1);
				}
			} else {
				MSAUtil masUtil = new MSAUtil(AcknowledgmentCode.AE.name(), "未找到患者基本信息");
				masUtil.setMsa(msa);
			}
	
			if (EmptyUtil.isNotNull(adr)) result = parser.encode(adr);
		} catch (HL7Exception e){
		} catch (IOException e){
		}
		return result;
	}
	
	/**
	 * QRY消息处理完成后使用此应答回复ADR消息
	 * 
	 * @param messageTriggerEvent 消息触发事件插入MSG-9-2. 例如: "A01"
	 * @param processingId 消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)
	 * @param version 版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param style HL7使用样式(xml或er7)
	 * @param acknowledgementCode AcknowledgmentCode枚举对象值
	 * @param textMessage 返回消息内容
	 * @param receivingApplication 接收应用名称
	 * @return ADR消息
	 * @return
	 */
	public static String encode(String messageTriggerEvent, String processingId, String version, String style, String acknowledgementCode, String textMessage, String receivingApplication) {
		Parser parser = hapiContext.getPipeParser();
		if ("xml".equals(style.toLowerCase())) {
			parser = hapiContext.getXMLParser();
		}

		AbstractMessage adr = null;

		AbstractSegment msh = null;
		AbstractSegment msa = null;

		String result = "";
		try{
			if (("2.1").equals(version) || ("v2.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v21.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v21.message.ADR_A19) adr).getMSA();
			} else if (("2.2").equals(version) || ("v2.2").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v22.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v22.message.ADR_A19) adr).getMSA();
			} else if (("2.3").equals(version) || ("v2.3").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v23.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v23.message.ADR_A19) adr).getMSA();
			} else if (("2.3.1").equals(version) || ("v2.3.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v231.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v231.message.ADR_A19) adr).getMSA();
			} else if (("2.4").equals(version) || ("v2.4").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v24.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v24.message.ADR_A19) adr).getMSA();
			} else if (("2.5").equals(version) || ("v2.5").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v25.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v25.message.ADR_A19) adr).getMSA();
			} else if (("2.5.1").equals(version) || ("v2.5.1").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v251.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v251.message.ADR_A19) adr).getMSA();
			} else if (("2.6").equals(version) || ("v2.6").equals(version)) {
				adr = new ca.uhn.hl7v2.model.v26.message.ADR_A19();
				adr.initQuickstart(messageCode, messageTriggerEvent, processingId);
	
				msh = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getMSH();
				msa = ((ca.uhn.hl7v2.model.v26.message.ADR_A19) adr).getMSA();
			}
			
			if (EmptyUtil.isNotNull(msh)) {
				MSHUtil mshUtil = new MSHUtil(HL7Constants.SENDING_APPLICATION, receivingApplication);
				mshUtil.setMsh(msh);
			}
			if (EmptyUtil.isNotNull(msa)) {
				MSAUtil msaUtil = new MSAUtil(acknowledgementCode, textMessage);
				msaUtil.setMsa(msa);
			}
			
			if (EmptyUtil.isNotNull(adr)) result = parser.encode(adr);
		} catch (HL7Exception e){
		} catch (IOException e){
		}
		return result;
	}
}
