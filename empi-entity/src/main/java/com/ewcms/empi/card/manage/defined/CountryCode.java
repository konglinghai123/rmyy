package com.ewcms.empi.card.manage.defined;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author wu_zhijun
 */
public enum CountryCode {
	
	CHN("China", "中国"),
	DEU("Germany", "德国"),
	JPN("Japan", "日本"),
	KOR("Korea, Republic of", "韩国"),
	USA("United States", "美国"),
	TWN("Taiwan, Province of China", "台湾")
	;
	
	private final String enName;
	private final String cnName;
	
	CountryCode(final String enName, final String cnName){
		this.enName = enName;
		this.cnName = cnName;
	}
	
	public String getEnName(){
		return enName;
	}
	
	public String getCnName(){
		return cnName;
	}
	
	public static String toStringAllSex(){
		return Arrays.toString(Sex.values());
	}
	
	public static CountryCode valueByCnName(String cnName){
		cnName = formatCnName(cnName);
		for (CountryCode event : values()){
			if (event.getCnName().equals(cnName)){
				return event;
			}
		}
		
		return CountryCode.CHN;
	}
	
	private static String formatCnName(String cnName){
		if (StringUtils.isBlank(cnName)){
			return cnName;
		}
		return cnName.trim().toLowerCase().replace("   ", "  ");
	}

	public static CountryCode valueByEnName(String enName) {
		enName = formatEnName(enName);
		for (CountryCode event : values()){
			if (event.getEnName().equals(enName)){
				return event;
			}
		}
		
		return CountryCode.CHN;
	}
	
	private static String formatEnName(String enName){
		if (StringUtils.isBlank(enName)){
			return enName;
		}
		return enName.trim().toLowerCase().replace("   ", "  ");
	}
}
