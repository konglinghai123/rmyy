package com.ewcms.yjk.zd.commonname.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.ConvertUtil;
import com.ewcms.util.ExcelUtil;
import com.ewcms.util.PinYin;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.repository.CommonNameRepository;
import com.google.common.collect.Lists;

/**
 *@author zhoudongchu
 */
@Service
public class CommonNameService extends BaseService<CommonName, Long> {
    private CommonNameRepository getCommonNameRepository() {
        return (CommonNameRepository) baseRepository;
    }

    public List<CommonName> findCommonNameBySpell(String spell){
    	return getCommonNameRepository().findCommonNameBySpell(spell);
    }
    
    public List<CommonName> findCommonNameByName(String commonName){
    	return getCommonNameRepository().findCommonNameByName(commonName);
    }
	@Override
	public CommonName save(CommonName m) {
		PinYin.initSpell(m);
		return super.save(m);
	}

	@Override
	public CommonName update(CommonName m) {
		PinYin.initSpell(m);
		return super.update(m);
	}
    
	public List<String> importExcel(InputStream in){
		List<String> notSave = Lists.newArrayList();
		
		List<CommonName> commonNames = ExcelUtil.importCommonName(in);
		for (CommonName commonName : commonNames) {
			try {
				super.save(commonName);
			} catch (Exception e) {
				notSave.add(commonName.getCommonName());
			}
		} 
		return notSave;
	}
}
