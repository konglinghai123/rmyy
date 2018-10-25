package com.ewcms.util;

import com.ewcms.common.utils.ConvertUtil;
import com.ewcms.yjk.zd.commonname.entity.CommonName;

public class PinYin {
	
    public static void initSpell(CommonName m){
    	m.setSpell(ConvertUtil.pinYin(m.getCommonName()).toLowerCase());
		m.setSpellSimplify(ConvertUtil.pinYinSimplify(m.getCommonName()).toLowerCase());
    }

}
