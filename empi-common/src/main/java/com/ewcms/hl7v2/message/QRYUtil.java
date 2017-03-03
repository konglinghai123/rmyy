package com.ewcms.hl7v2.message;

import java.io.IOException;
import java.util.Calendar;

import com.ewcms.hl7v2.MessageTriggerEvent;
import com.ewcms.hl7v2.model.QRYEntity;
import com.ewcms.hl7v2.util.HL7StringUtil;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.Parser;

/**
 * 查询消息
 * 
 * @author wu_zhijun
 */
public class QRYUtil {
	
	private static HapiContext hapiContext = new DefaultHapiContext();
	
	/**
	 * 就诊卡号转化为QRY消息
	 * 
	 * @param practiceNo 就诊卡号
	 * @param version 版本号(2.1或v2.1, 2.2或v2.2, 2.3或v2.3, ,2.3.1或v2.3.1, 2.4或v2.4, 2.5或v2.5, 2.5.1或v2.5.1, 2.6或v2.6)
	 * @param processingId 消息处理ID插入MSH-11. 例如: "T" (for TEST) or "P" for (PRODUCTION)
	 * @param style HL7使用样式(xml或er7)
	 * @return QRY消息
	 */
	public static String encode(String practiceNo, String version, String processingId, String style) {
		Parser parser = hapiContext.getPipeParser();
		if ("xml".equals(style.toLowerCase())){
			parser = hapiContext.getXMLParser();
		}
		
		String result = "";
		try{
			if ("2.1".equals(version) || "v2.1".equals(version)){
				result = v21Encode(practiceNo, processingId, parser);
			} else if ("2.2".equals(version) || "v2.2".equals(version)){
				result = v22Encode(practiceNo, processingId, parser);
			} else if ("2.3".equals(version) || "v2.3".equals(version)){
				result = v23Encode(practiceNo, processingId, parser);
			} else if ("2.3.1".equals(version) || "v2.3.1".equals(version)){
				result = v231Encode(practiceNo, processingId, parser);
			} else if ("2.4".equals(version) || "v2.4".equals(version)){
				result = v24Encode(practiceNo, processingId, parser);
			} else if ("2.5".equals(version) || "v2.5".equals(version)){
				result = v25Encode(practiceNo, processingId, parser);
			} else if ("2.5.1".equals(version) || "v2.5.1".equals(version)){
				result = v251Encode(practiceNo, processingId, parser);
			} else if ("2.6".equals(version) || "v2.6".equals(version)){
				result = v26Encode(practiceNo, processingId, parser);
			}
		} catch (HL7Exception e){
		} catch (IOException e) {
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
	
	/*******************************************encode***************************************************/
	private static String v21Encode(String practiceNo, String processingId, Parser parser) throws HL7Exception, IOException{
		ca.uhn.hl7v2.model.v21.message.QRY_A19 qry = new ca.uhn.hl7v2.model.v21.message.QRY_A19();
		qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);
		
		ca.uhn.hl7v2.model.v21.segment.QRD qrd = qry.getQRD();
		
		qrd.getQrd4_QUERYID().setValue(practiceNo);
		return parser.encode(qry);
	}

	private static String v22Encode(String practiceNo, String processingId, Parser parser) throws HL7Exception, IOException{
		ca.uhn.hl7v2.model.v22.message.QRY_A19 qry = new ca.uhn.hl7v2.model.v22.message.QRY_A19();
		qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

		ca.uhn.hl7v2.model.v22.segment.QRD qrd = qry.getQRD();
		qrd.getQrd4_QueryID().setValue(practiceNo);
		return parser.encode(qry);
	}

	private static String v23Encode(String practiceNo, String processingId, Parser parser) throws HL7Exception, IOException{
		ca.uhn.hl7v2.model.v23.message.QRY_A19 qry = new ca.uhn.hl7v2.model.v23.message.QRY_A19();
		qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

		ca.uhn.hl7v2.model.v23.segment.QRD qrd = qry.getQRD();
		qrd.getQrd4_QueryID().setValue(practiceNo);
		return parser.encode(qry);
	}

	private static String v231Encode(String practiceNo, String processingId, Parser parser) throws HL7Exception, IOException{
		ca.uhn.hl7v2.model.v231.message.QRY_A19 qry = new ca.uhn.hl7v2.model.v231.message.QRY_A19();
		qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

		ca.uhn.hl7v2.model.v231.segment.QRD qrd = qry.getQRD();
		qrd.getQrd4_QueryID().setValue(practiceNo);
		return parser.encode(qry);
	}
	
	private static String v24Encode(String practiceNo, String processingId, Parser parser) throws HL7Exception, IOException{
		ca.uhn.hl7v2.model.v24.message.QRY_A19 qry = new ca.uhn.hl7v2.model.v24.message.QRY_A19();
		qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

		ca.uhn.hl7v2.model.v24.segment.MSH msh = qry.getMSH();
		msh.getMsh7_DateTimeOfMessage().getTs1_TimeOfAnEvent().setValue(Calendar.getInstance().getTime());
		
		ca.uhn.hl7v2.model.v24.segment.QRD qrd = qry.getQRD();
		qrd.getQrd4_QueryID().setValue(practiceNo);
		return parser.encode(qry);
	}
	
	private static String v25Encode(String practiceNo, String processingId, Parser parser) throws HL7Exception, IOException{
		ca.uhn.hl7v2.model.v25.message.QRY_A19 qry = new ca.uhn.hl7v2.model.v25.message.QRY_A19();
		qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

		ca.uhn.hl7v2.model.v25.segment.QRD qrd = qry.getQRD();
		qrd.getQrd4_QueryID().setValue(practiceNo);
		return parser.encode(qry);
	}
	
	private static String v251Encode(String practiceNo, String processingId, Parser parser) throws HL7Exception, IOException{
		ca.uhn.hl7v2.model.v251.message.QRY_A19 qry = new ca.uhn.hl7v2.model.v251.message.QRY_A19();
		qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

		ca.uhn.hl7v2.model.v251.segment.QRD qrd = qry.getQRD();
		qrd.getQrd4_QueryID().setValue(practiceNo);
		return parser.encode(qry);
	}

	private static String v26Encode(String practiceNo, String processingId, Parser parser) throws HL7Exception, IOException{
		ca.uhn.hl7v2.model.v26.message.QRY_A19 qry = new ca.uhn.hl7v2.model.v26.message.QRY_A19();
		qry.initQuickstart(MessageTriggerEvent.A19.getCode(), MessageTriggerEvent.A19.getTriggerEvent(), processingId);

		ca.uhn.hl7v2.model.v26.segment.QRD qrd = qry.getQRD();
		qrd.getQrd4_QueryID().setValue(practiceNo);
		return parser.encode(qry);
	}

	/*******************************************parser***************************************************/
	private static QRYEntity v21Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v21.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v21.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v21.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v21.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SENDINGAPPLICATION().getValue());
		
		ca.uhn.hl7v2.model.v21.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd4_QUERYID().getValue());
		
		return qryEntity;
	}
	
	private static QRYEntity v22Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v22.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v22.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v22.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v22.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getValue());
		
		ca.uhn.hl7v2.model.v22.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd4_QueryID().getValue());
		
		return qryEntity;
	}
	
	private static QRYEntity v23Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v23.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v23.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v23.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v23.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		
		ca.uhn.hl7v2.model.v23.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd4_QueryID().getValue());
		
		return qryEntity;
	}
	
	private static QRYEntity v231Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v231.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v231.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v231.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v231.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		
		ca.uhn.hl7v2.model.v231.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd4_QueryID().getValue());
		
		return qryEntity;

	}
	
	private static QRYEntity v24Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v24.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v24.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v24.message.QRY_A19) msg;

		ca.uhn.hl7v2.model.v24.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		
		ca.uhn.hl7v2.model.v24.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd4_QueryID().getValue());
		
		return qryEntity;

	}
	
	private static QRYEntity v25Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v25.message.QRY_A19.class));
		Message msg = parser.parse(message);
		
		ca.uhn.hl7v2.model.v25.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v25.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v25.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		
		ca.uhn.hl7v2.model.v25.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd4_QueryID().getValue());
		
		return qryEntity;
	}
	
	private static QRYEntity v251Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v251.message.QRY_A19.class));
		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v251.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v251.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v251.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		
		ca.uhn.hl7v2.model.v251.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd4_QueryID().getValue());
		
		return qryEntity;
	}
	
	private static QRYEntity v26Parser(String message, Parser parser) throws HL7Exception{
		QRYEntity qryEntity = new QRYEntity();
		hapiContext.setModelClassFactory(new CanonicalModelClassFactory(ca.uhn.hl7v2.model.v26.message.QRY_A19.class));
		Message msg = parser.parse(message);

		ca.uhn.hl7v2.model.v26.message.QRY_A19 qry = (ca.uhn.hl7v2.model.v26.message.QRY_A19) msg;
		
		ca.uhn.hl7v2.model.v26.segment.MSH msh = qry.getMSH();
		qryEntity.setReceivingApplication(msh.getMsh3_SendingApplication().getHd1_NamespaceID().getValue());
		
		ca.uhn.hl7v2.model.v26.segment.QRD qrd = qry.getQRD();
		qryEntity.setPracticeNo(qrd.getQrd4_QueryID().getValue());
		
		return qryEntity;

	}
}
