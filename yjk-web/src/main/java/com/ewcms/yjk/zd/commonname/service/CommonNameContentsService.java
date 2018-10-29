package com.ewcms.yjk.zd.commonname.service;

import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;
import com.ewcms.common.service.BaseService;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;

/**
 *@author zhoudongchu
 */
@Service
public class CommonNameContentsService extends BaseService<CommonNameContents, Long> {
	
	@Override
	public CommonNameContents update(CommonNameContents m) {
		m.setUpdateDate(new Date(Calendar.getInstance().getTime().getTime()));
		return super.update(m);
	}
}
