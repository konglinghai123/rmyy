package com.ewcms.hl7v2.message;

import java.io.IOException;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.HL7StringUtil;
import com.ewcms.hl7v2.HL7Constants;
import com.ewcms.hl7v2.defined.MessageTriggerEvent;
import com.ewcms.hl7v2.model.ACKEntity;
import com.ewcms.hl7v2.model.QRYEntity;
import com.ewcms.hl7v2.segment.MSHSegment;
import com.ewcms.hl7v2.segment.QRDSegment;

import ca.uhn.hl7v2.AcknowledgmentCode;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.AbstractSegment;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.Parser;

/**
 * 查询消息
 * 
 * @author wu_zhijun
 */
public class QRYMessage {
	
	private static HapiContext hapiContext = new DefaultHapiContext();
	
	/**
	 * 构造QRY消息
	 * 
	 * @param qryEntity QRY消息实体
	 * @return QRY消息
	 */
	public static String encode(QRYEntity qryEntity) {
		ACKEntity ackEntity = new ACKEntity();
		ackEntity.setAcknowledgmentCode(AcknowledgmentCode.AE.name());
		ackEntity.setMessageTriggerEvent(MessageTriggerEvent.A19.getTriggerEvent());
		
		String result = "";
		try{
			String practiceNo = qryEntity.getPracticeNo();
			String messageControlId = qryEntity.getMessageControlId();
			String processingId = qryEntity.getProcessingId();
			String receivingApplication = qryEntity.getReceivingApplication();
			String version = qryEntity.getVersion();
			String style = qryEntity.getStyle();
			
			ackEntity.setMessageControlId(messageControlId);
			ackEntity.setProcessingId(processingId);
			ackEntity.setReceivingApplication(receivingApplication);
			ackEntity.setVersion(version);
			ackEntity.setStyle(style);
			if (EmptyUtil.isStringEmpty(practiceNo)){
				ackEntity.setTextMessage("传递的患者卡号为空，请重新传递");
				return ADRMessage.encode(ackEntity);
			}
			
			Parser parser = hapiContext.getPipeParser();
			if ("xml".equals(qryEntity.getStyle().toLowerCase())) {
				parser = hapiContext.getXMLParser();
			}

			AbstractMessage qry = null;

			AbstractSegment msh = null;
			AbstractSegment qrd = null;
			
			if (("2.1").equals(version) || ("v2.1").equals(version)) {
				qry = new ca.uhn.hl7v2.model.v21.message.QRY_A19();
				qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

				msh = ((ca.uhn.hl7v2.model.v21.message.QRY_A19) qry).getMSH();
				qrd = ((ca.uhn.hl7v2.model.v21.message.QRY_A19) qry).getQRD();
			} else if (("2.2").equals(version) || ("v2.2").equals(version)) {
				qry = new ca.uhn.hl7v2.model.v22.message.QRY_A19();
				qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

				msh = ((ca.uhn.hl7v2.model.v22.message.QRY_A19) qry).getMSH();
				qrd = ((ca.uhn.hl7v2.model.v22.message.QRY_A19) qry).getQRD();
			} else if (("2.3").equals(version) || ("v2.3").equals(version)) {
				qry = new ca.uhn.hl7v2.model.v23.message.QRY_A19();
				qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

				msh = ((ca.uhn.hl7v2.model.v23.message.QRY_A19) qry).getMSH();
				qrd = ((ca.uhn.hl7v2.model.v23.message.QRY_A19) qry).getQRD();

			} else if (("2.3.1").equals(version) || ("v2.3.1").equals(version)) {
				qry = new ca.uhn.hl7v2.model.v231.message.QRY_A19();
				qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

				msh = ((ca.uhn.hl7v2.model.v231.message.QRY_A19) qry).getMSH();
				qrd = ((ca.uhn.hl7v2.model.v231.message.QRY_A19) qry).getQRD();
			} else if (("2.4").equals(version) || ("v2.4").equals(version)) {
				qry = new ca.uhn.hl7v2.model.v24.message.QRY_A19();
				qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

				msh = ((ca.uhn.hl7v2.model.v24.message.QRY_A19) qry).getMSH();
				qrd = ((ca.uhn.hl7v2.model.v24.message.QRY_A19) qry).getQRD();
			} else if (("2.5").equals(version) || ("v2.5").equals(version)) {
				qry = new ca.uhn.hl7v2.model.v25.message.QRY_A19();
				qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

				msh = ((ca.uhn.hl7v2.model.v25.message.QRY_A19) qry).getMSH();
				qrd = ((ca.uhn.hl7v2.model.v25.message.QRY_A19) qry).getQRD();
			} else if (("2.5.1").equals(version) || ("v2.5.1").equals(version)) {
				qry = new ca.uhn.hl7v2.model.v251.message.QRY_A19();
				qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

				msh = ((ca.uhn.hl7v2.model.v251.message.QRY_A19) qry).getMSH();
				qrd = ((ca.uhn.hl7v2.model.v251.message.QRY_A19) qry).getQRD();
			} else if (("2.6").equals(version) || ("v2.6").equals(version)) {
				qry = new ca.uhn.hl7v2.model.v26.message.QRY_A19();
				qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

				msh = ((ca.uhn.hl7v2.model.v26.message.QRY_A19) qry).getMSH();
				qrd = ((ca.uhn.hl7v2.model.v26.message.QRY_A19) qry).getQRD();
			} else {
				throw new HL7Exception("The version used is out of range");
			}
			
			if (EmptyUtil.isNotNull(msh)) {
				MSHSegment mshSegment = new MSHSegment(HL7Constants.SENDING_APPLICATION, receivingApplication);
				mshSegment.setMshSegment(msh);
			}
	
			if (EmptyUtil.isNotNull(qrd)) {
				QRDSegment qrdSegment = new QRDSegment(practiceNo);
				qrdSegment.setQrdSegment(qrd);
			}

			result = parser.encode(qry);
		} catch (HL7Exception e){
			ackEntity.setTextMessage("HL7消息错误");
			result = ADRMessage.encode(ackEntity);
		} catch (IOException e){
			ackEntity.setTextMessage("HL7消息错误");
			result = ADRMessage.encode(ackEntity);
		} catch (Exception e){
			ackEntity.setTextMessage("HL7消息错误");
			result = ADRMessage.encode(ackEntity);
		}
		return result;
	}
	
	/**
	 * QRY消息转化为就诊卡号
	 * 
	 * @param message QRY消息
	 * @param version 版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param style HL7使用样式(xml或er7)
	 * @return 就诊卡号
	 * @throws HL7Exception
	 */
	public static QRYEntity parser(String message, String version, String style) throws HL7Exception{
		Parser parser = hapiContext.getXMLParser();
		if (!"xml".equals(style.toLowerCase())){
			parser = hapiContext.getPipeParser();
			message = HL7StringUtil.format(message);
		}
		
		if ("2.1".equals(version) || "v2.1".equals(version)){
			return v21Parser(message, parser);
		} else if ("2.2".equals(version) || "v2.2".equals(version)){
			return v22Parser(message, parser);
		} else if ("2.3".equals(version) || "v2.3".equals(version)){
			return v23Parser(message, parser);
		} else if ("2.3.1".equals(version) || "v2.3.1".equals(version)){
			return v231Parser(message, parser);
		} else if ("2.4".equals(version) || "v2.4".equals(version)){
			return v24Parser(message, parser);
		} else if ("2.5".equals(version) || "v2.5".equals(version)){
			return v25Parser(message, parser);
		} else if ("2.5.1".equals(version) || "v2.5.1".equals(version)){
			return v251Parser(message, parser);
		} else if ("2.6".equals(version) || "v2.6".equals(version)){
			return v26Parser(message, parser);
		} else {
			throw new HL7Exception("The version used is out of range");
		}
	}
	
	/*******************************************parser***************************************************/
	private static QRYEntity v21Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v21.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v21.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v21.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v21.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SENDINGAPPLICATION().getValue());
		qryEntity.setMessageControlId(msh.getMsh10_MESSAGECONTROLID().getValue());
		
		ca.uhn.hl7v2.model.v21.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd8_WHOSUBJECTFILTER(0).getValue());
		
		return qryEntity;
	}
	
	private static QRYEntity v22Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v22.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v22.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v22.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v22.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getValue());
		qryEntity.setMessageControlId(msh.getMsh10_MessageControlID().getValue());
		
		ca.uhn.hl7v2.model.v22.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd8_WhoSubjectFilter(0).getValue());
		
		return qryEntity;
	}
	
	private static QRYEntity v23Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v23.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v23.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v23.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v23.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		qryEntity.setMessageControlId(msh.getMsh10_MessageControlID().getValue());
		
		ca.uhn.hl7v2.model.v23.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd8_WhoSubjectFilter(0).getXcn1_IDNumber().getValue());
		
		return qryEntity;
	}
	
	private static QRYEntity v231Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v231.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v231.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v231.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v231.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		qryEntity.setMessageControlId(msh.getMsh10_MessageControlID().getValue());
		
		ca.uhn.hl7v2.model.v231.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd8_WhoSubjectFilter(0).getXcn1_IDNumber().getValue());
		
		return qryEntity;

	}
	
	private static QRYEntity v24Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v24.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v24.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v24.message.QRY_A19) msg;

		ca.uhn.hl7v2.model.v24.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		qryEntity.setMessageControlId(msh.getMsh10_MessageControlID().getValue());
		
		ca.uhn.hl7v2.model.v24.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd8_WhoSubjectFilter(0).getXcn1_IDNumber().getValue());
		
		return qryEntity;

	}
	
	private static QRYEntity v25Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v25.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v25.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v25.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v25.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		qryEntity.setMessageControlId(msh.getMsh10_MessageControlID().getValue());
		
		ca.uhn.hl7v2.model.v25.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd8_WhoSubjectFilter(0).getXcn1_IDNumber().getValue());
		
		return qryEntity;
	}
	
	private static QRYEntity v251Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v251.message.QRY_A19.class));
		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v251.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v251.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v251.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		qryEntity.setMessageControlId(msh.getMsh10_MessageControlID().getValue());
		
		ca.uhn.hl7v2.model.v251.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd8_WhoSubjectFilter(0).getXcn1_IDNumber().getValue());
		
		return qryEntity;
	}
	
	private static QRYEntity v26Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v26.message.QRY_A19.class));
		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v26.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v26.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v26.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		qryEntity.setMessageControlId(msh.getMsh10_MessageControlID().getValue());
		
		ca.uhn.hl7v2.model.v26.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd8_WhoSubjectFilter(0).getXcn1_IDNumber().getValue());
		
		return qryEntity;

	}
}
