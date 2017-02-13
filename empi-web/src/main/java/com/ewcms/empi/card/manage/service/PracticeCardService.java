package com.ewcms.empi.card.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.exception.BaseException;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.repository.PracticeCardRepository;

/**
 *@author zhoudongchu
 */
@Service
public class PracticeCardService extends BaseService<PracticeCard, Long> {
	private PracticeCardRepository getPracticeCardRepository() {
		return (PracticeCardRepository) baseRepository;
	}
	
	@Autowired
	private PatientBaseInfoService patientBaseInfoService;

	public PracticeCard findByPracticeNo(String practiceNo){
		return getPracticeCardRepository().findByPracticeNo(practiceNo);
	} 

	/**
	 * 发诊疗卡
	 * 
	 * @param m 诊疗卡对象
	 * @param userId 操作员ID
	 * @return 诊疗卡
	 */
	public PracticeCard distribute(PracticeCard m) {
		PracticeCard dbPracticeCard = getPracticeCardRepository().findByPracticeNo(m.getPracticeNo());
		if(EmptyUtil.isNotNull(dbPracticeCard))throw new BaseException("诊疗卡号已存在");
		String certificateNo = m.getPatientBaseInfo().getCertificateNo();
		if (EmptyUtil.isNull(certificateNo)) throw new BaseException("患者基本信息不存在");
		PracticeCard practiceCard = null;
//		TODO 除了使用certificateNo（证件号码）外，还可以使用姓名+电话号码等形式判断信息存在与否和查询患者基本信息
		PatientBaseInfo dbPatientBaseInfo = patientBaseInfoService.findByCertificateNo(certificateNo);
		
		if (EmptyUtil.isNull(dbPatientBaseInfo)){//数据库找不到患者信息

			return super.save(m);//新建诊疗卡和患者信息
		}else{
			PatientBaseInfo currentPatientBaseInfo =m.getPatientBaseInfo(); 
			m.setPatientBaseInfo(dbPatientBaseInfo);
			practiceCard = super.save(m);//新建诊疗卡信息
			
			currentPatientBaseInfo.setId(dbPatientBaseInfo.getId());
			practiceCard.setPatientBaseInfo(currentPatientBaseInfo);
			//TODO 如果页面未读取患者信息，那么页面新的患者信息会覆盖掉原有的患者信息
			return super.update(practiceCard);//更新患者信息
		}
	}
}
