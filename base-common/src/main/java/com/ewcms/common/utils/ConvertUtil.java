package com.ewcms.common.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 转换工具类
 * 
 * @author wuzhijun
 *
 */
public class ConvertUtil {
	/**
	 * 中文转换成拼音
	 * 
	 * @param chinese 中文名
	 * @return String 拼音
	 */
	public static String pinYin(String chinese){
		String pinYin = "";
		
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		
		char[] charArray = chinese.toCharArray();
		try {
			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] > 128) {
					try {
						pinYin += PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat)[0];
					} catch (NullPointerException e) {
						pinYin += charArray[i];
					}
				} else {
					pinYin += charArray[i];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
		}
		return pinYin;
	}
	
	/**
	 * 中文转换成拼音首字母简写
	 * 
	 * @param chinese 中文名
	 * @return String 拼音首字母简写
	 */
	public static String pinYinSimplify(String chinese){
		String pinYin = "";
		
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		
		char[] charArray = chinese.toCharArray();
		try {
			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] > 128) {
					try {
						pinYin += PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat)[0].charAt(0);
					} catch (NullPointerException e) {
						pinYin += charArray[i];
					} 
				} else {
					pinYin += charArray[i];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
		}
		return pinYin;
	}	
	/**
	 * 
	 * @param descriptions
	 * @param prefix
	 * @return
	 */
	public static Map<Long, String> resource(String descriptions, String prefix){
		Map<Long, String> map = new HashMap<Long, String>();
		String[] prefixs = descriptions.split(prefix);
		if (prefixs != null && prefixs.length > 0){
			for (String prefixValue : prefixs){
				System.out.println(prefixValue);
				if (prefixValue != null && prefixValue.trim().length() > 0){
					String replaceValue = prefixValue.replaceAll("%5B", "").replaceAll("%5D", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("ck=", "").replaceAll("&", "");
					String[] keyValues = replaceValue.split("=");
					if (keyValues.length != 2) continue;
					try{
						Long key = Long.parseLong(keyValues[0]);
						map.put(key, keyValues[1]);
					}catch(Exception e){
					}
				}
			}
		}
		return map;
	}
	
	public static void main(String[] args){
		String descriptions = "ck=&descriptions%5B12%5D=abc.jpg&ck=&descriptions%5B13%5D=def.jpg&ck=&ck=&descriptions[14]=def.jpg";
		Map<Long, String> map = resource(descriptions, "descriptions");
		Iterator<Entry<Long, String>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<Long, String> entry = it.next();
			System.out.println("key : " + entry.getKey() + " value : "  + entry.getValue());
		}
		
		System.out.println(pinYin("人凝血因子Ⅷ"));
		System.out.println(pinYin("ω-3鱼油脂肪乳"));
		System.out.println(pinYin("25%葡萄糖"));
		System.out.println(pinYin("草分枝杆菌F.U.36"));
		System.out.println(pinYin("多维元素（21）"));
		
		Calendar calendar = Calendar.getInstance();
		System.out.println(String.format("'F':将时间格式化为：2016-10-19。输出：%tF %tT", calendar, calendar));
	}
}
