package com.ewcms.util;

import com.ewcms.common.utils.ConvertUtil;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;

public class PinYin {
	
    public static void initSpell(CommonName m){
    	m.setSpell(ConvertUtil.pinYin(m.getCommonName()).toLowerCase());
		m.setSpellSimplify(ConvertUtil.pinYinSimplify(m.getCommonName()).toLowerCase());
    }
    
    public static void initSpell(CommonNameContents m){
    	m.setSpell(ConvertUtil.pinYin(m.getCommonName()).toLowerCase());
		m.setSpellSimplify(ConvertUtil.pinYinSimplify(m.getCommonName()).toLowerCase());
    }
    
    public static void initSpell(HospitalContents m){
    	m.setSpell(ConvertUtil.pinYin(m.getCommonName()).toLowerCase());
		m.setSpellSimplify(ConvertUtil.pinYinSimplify(m.getCommonName()).toLowerCase());
    }
}
