package com.ewcms.hl7v2.defined;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import ca.uhn.hl7v2.HL7Exception;

/**
 * ADT定义消息
 *
 * @author wu_zhijun
 */
public enum MessageTriggerEvent {

	A01("A01", "ADT", "Admit a Patient", "接收患者"),
	A02("A02", "ADT", "Transfer a Patient", "转移患者"),
	A03("A03", "ADT", "Discharge a Patient", "患者出院"),
	A04("A04", "ADT", "Register a Patient", "患者登记"),
	A05("A05", "ADT", "Pre-Admit a Patient", "接纳患者前"),
	A06("A06", "ADT", "Transfer an Outpatient-to Inpatient", "门诊患者转为住院患者"),
	A07("A07", "ADT", "Transfer an Inpatient-to Outpatient", "住院者转为门诊患者"),
	A08("A08", "ADT", "Update Patient Information", "更新患者信息"),
	A09("A09", "ADT", "Patient Departing", "患者出院"),
	A10("A10", "ADT", "Patient Arriving", "患者到达"),
	A11("A11", "ADT", "Cancel Admit", "取消接收"),
	A12("A12", "ADT", "Cancel Transfer", "取消转移"),
	A13("A13", "ADT", "Cancel Discharge", "取消出院"),
	A14("A14", "ADT", "Pending Admit", "未决定接收"),
	A15("A15", "ADT", "Pending Transfer", "未决定转移"),
	A16("A16", "ADT", "Pending Discharge", "未决定出院"),
	A17("A17", "ADT", "Swap Patients", "交换患者"),
	A18("A18", "ADT", "Merge Patient Information", "合并患者信息"),
	A19("A19", "ADR", "Patient Query", "患者查询"),
	A20("A20", "ADT", "Bed Status Update", "病床情况更新"),
	A21("A21", "ADT", "Patient Goes on \"Leave Of Absence\"", "患者休假"),
	A22("A22", "ADT", "Patient Returns from LOAD", "患者休假归来"),
	A23("A23", "ADT", "Delete a Patient Record", "删除患者记录"),
	A24("A24", "ADT", "Create a Patient Link", "建立患者连接"),
	A25("A25", "ADT", "Cancel Pending Discharge", "取消未决定出院"),
	A26("A26", "ADT", "Cancel Pending Transfer", "取消未决定转院"),
	A27("A27", "ADT", "Cancel Pending Admit", "取消未决定入院"),
	A28("A28", "ADT", "Add Person Information", "加入人员信息"),
	A29("A29", "ADT", "Delete Person Information", "删除人员信息"),
	A30("A30", "ADT", "Merge Person Information", "合并人员信息"),
	A31("A31", "ADT", "Update Person Information", "更新人员信息"),
	A32("A32", "ADT", "Cancel Patient Arriving", "取消患者到来"),
	A33("A33", "ADT", "Cancel Patient Departing", "取消患者出院"),
	A34("A34", "ADT", "Merge Patient Information - ID Only", "合并患者信息-病历号"),
	A35("A35", "ADT", "Merge Patient Info - Acct.# Only", "合并患者信息-账号"),
	A36("A36", "ADT", "Merge Pat. Info - Pat. ID & Acct.#", "合并患者信息-病历号、账号"),
	A37("A37", "ADT", "Un-Link Patient Information", "取消患者信息连接"),
	A38("A38", "ADT", "Cancel Pre-Admit", "取消准入"),
	A39("A39", "ADT", "Merge Person - External ID", "合并人员-外部ID"),
	A40("A40", "ADT", "Merge Person - Internal ID", "合并人员-内部ID"),
	A41("A41", "ADT", "Merge Account - Patient Account Number", "合并账号-患者账号数"),
	A42("A42", "ADT", "Merge Visit - Visit Number", "合并来访者-来访人数"),
	A43("A43", "ADT", "Move Patient Information - Internal ID", "移动患者信息-内部ID"),
	A44("A44", "ADT", "Move Account Information - Patient Account Number", "移动账号信息-患者账号数"),
	A45("A45", "ADT", "Move Visit Information - Visit Number", "移动来访者信息-来访者者从数"),
	A46("A46", "ADT", "Change External ID", "更改外部ID"),
	A47("A47", "ADT", "Change Internal ID", "更改内部ID"),
	A48("A48", "ADT", "Change Alternate Patient ID", "更改预备患者ID"),
	A49("A49", "ADT", "Change Patient Account Number", "更改患者账号"),
	A50("A50", "ADT", "Change Visit Number", "更改来访人数"),
	A51("A51", "ADT", "Change Alternate Visit ID", "更改预备来访者ID"),
	A52("A52", "ADT", "Cancel Leave of Absence fro a Patient", "取消患者暂离"),
	A53("A53", "ADT", "Cancel Patient Returns from a Leave of Absence", "取消患者请假后返回"),
	A54("A54", "ADT", "Change Attending Doctor", "改变主治医生"),
	A55("A55", "ADT", "Cancel Change Attending Doctor", "取消改变主治医生"),
	;
	
	private final String triggerEvent;
	private final String code;
	private final String enExplain;
	private final String zhCnExplain;
	
	MessageTriggerEvent(final String triggerEvent, final String code, final String enExplain, final String znExplain){
		this.triggerEvent = triggerEvent;
		this.code = code;
		this.enExplain = enExplain;
		this.zhCnExplain = znExplain;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getTriggerEvent(){
		return triggerEvent;
	}
	
	public String getEnExplain(){
		return enExplain;
	}
	
	public String getZnCnExplain(){
		return zhCnExplain;
	}
	
	public static String toStringAllTriggerEvent(){
		return Arrays.toString(MessageTriggerEvent.values());
	}
	
	public static MessageTriggerEvent valueByZhCnExplain(String zhCnExplain) throws HL7Exception{
		zhCnExplain = formatZhCnExplain(zhCnExplain);
		for (MessageTriggerEvent event : values()){
			if (event.getZnCnExplain().equals(zhCnExplain)){
				return event;
			}
		}
		
		throw new HL7Exception("MessageTriggerEvent not method event zhCnExplain : " + zhCnExplain);
	}
	
	private static String formatZhCnExplain(String zhCnExplain){
		if (StringUtils.isBlank(zhCnExplain)){
			return zhCnExplain;
		}
		return zhCnExplain.trim().toLowerCase().replace("   ", "  ");
	}

	public static MessageTriggerEvent valueByEnExplain(String enExplain) throws HL7Exception {
		enExplain = formatEnExplain(enExplain);
		for (MessageTriggerEvent event : values()){
			if (event.getEnExplain().equals(enExplain)){
				return event;
			}
		}
		
		throw new HL7Exception("MessageTriggerEvent not method event enExplain : " + enExplain);
	}
	
	private static String formatEnExplain(String enExplain){
		if (StringUtils.isBlank(enExplain)){
			return enExplain;
		}
		return enExplain.trim().toLowerCase().replace("   ", "  ");
	}
}
