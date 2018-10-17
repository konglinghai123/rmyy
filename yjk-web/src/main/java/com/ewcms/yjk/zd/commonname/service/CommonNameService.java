package com.ewcms.yjk.zd.commonname.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.ConvertUtil;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.repository.CommonNameRepository;

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
    
	@Override
	public CommonName save(CommonName m) {
		initSpell(m);
		return super.save(m);
	}

	@Override
	public CommonName update(CommonName m) {
		initSpell(m);
		return super.update(m);
	}
    
    private CommonName initSpell(CommonName m){
    	m.setSpell(ConvertUtil.pinYin(m.getCommonName()).toLowerCase());
		m.setSpellSimplify(ConvertUtil.pinYinSimplify(m.getCommonName()).toLowerCase());
		return m;
    }
}
