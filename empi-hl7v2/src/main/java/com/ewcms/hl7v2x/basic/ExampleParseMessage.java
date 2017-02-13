package com.ewcms.hl7v2x.basic;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import ca.uhn.hl7v2.parser.Parser;

/**
 *
 * @author wu_zhijun
 */
public class ExampleParseMessage {
	
	public static void main(String[] args){
		String msg = "MSH|^~\\&|HIS|RHI|EKG|EKG|199904140038||ADT^A01||P|2.2\r"
				+ "PID|0001|00009874|00001122|A00977|SMITH^JOHN^M|MOM|19581119|F|NOTREAL^LINDA^M|C|564 SPRING ST^^NEEDHAM^MA^02494^US|0002|(818)565-1551|(425)828-3344|E|S|C|0000444444|252-00-4414||||SA|||SA||||NONE|V1|0001|I|D.ER^50A^M110^01|ER|P00055|11B^M011^02|070615^BATMAN^GEORGE^L|555888^NOTREAL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^NOTREAL^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|199904101200||||5555112333|||666097^NOTREAL^MANNY^P\r"
				+ "NK1|0222555|NOTREAL^JAMES^R|FA|STREET^OTHER STREET^CITY^ST^55566|(222)111-3333|(888)999-0000|||||||ORGANIZATION\r"
				+ "PV1|0001|I|D.ER^1F^M950^01|ER|P000998|11B^M011^02|070615^BATMAN^GEORGE^L|555888^OKNEL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^VOICE^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|||||5555112333|||666097^DNOTREAL^MANNY^P\r"
				+ "PV2|||0112^TESTING|55555^PATIENT IS NORMAL|NONE|||19990225|19990226|1|1|TESTING|555888^NOTREAL^BOB^K^DR^MD||||||||||PROD^003^099|02|ER||NONE|19990225|19990223|19990316|NONE\r"
				+ "AL1||SEV|001^POLLEN\r"
				+ "GT1||0222PL|NOTREAL^BOB^B||STREET^OTHER STREET^CITY^ST^77787|(444)999-3333|(222)777-5555||||MO|111-33-5555||||NOTREAL GILL N|STREET^OTHER STREET^CITY^ST^99999|(111)222-3333\r"
				+ "IN1||022254P|4558PD|BLUE CROSS|STREET^OTHER STREET^CITY^ST^00990||(333)333-6666||221K|LENIX|||19980515|19990515|||PATIENT01 TEST D||||||||||||||||||02LL|022LP554";
		
		/**
		 * HapiContext是所有配置和提供获取各种HAPI对象的工厂方法，例如分析器
		 */
		HapiContext context = new DefaultHapiContext();
		
		/**
		 * Parser用于字符串表达示形式的信息与HAPI消息对象之间转换。在这种情况下，我们命名用的是“GenericParser”，它能够处理XML和ER7(pipe & hat)编码。
		 */
		Parser p = context.getGenericParser();
		
		Message hapiMsg;
		try{
			//解析方法执行实际解析
			hapiMsg = p.parse(msg);
		} catch (EncodingNotSupportedException e){
			e.printStackTrace();
			return;
		} catch (HL7Exception e){
			e.printStackTrace();
			return;
		}
		
		/**
		 * 这个消息是一个ADT^A01是HL7数据类型组成的几个组件，所以我们将它作为ADT_A01类的扩展消息为ADT^A01的部分。
		 * HAPI提供了不同版本的ADT_A01类，每一个不同的包（注意引入的声明）对应于该消息的HL7版本。
		 */
		ca.uhn.hl7v2.model.v22.message.ADT_A01 adtMsg = (ca.uhn.hl7v2.model.v22.message.ADT_A01)hapiMsg;
		
		ca.uhn.hl7v2.model.v22.segment.MSH msh = adtMsg.getMSH();
		
		//MSH检索一些数据
		String msgType = msh.getMessageType().getMessageType().getValue();
		String msgTrigger = msh.getMessageType().getTriggerEvent().getValue();
		
		System.out.println(msgType + " " + msgTrigger);
		
		ca.uhn.hl7v2.model.v22.datatype.PN patientName = adtMsg.getPID().getPatientName();
		String familyName = patientName.getFamilyName().getValue();
		System.out.println(familyName + "- -" + adtMsg.getPID().getSex().getValue() + "- -" + adtMsg.getPID().getBirthPlace().getValue() + "- -" + patientName.getGivenName().getValue());
	}
}
