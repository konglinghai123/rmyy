package com.ewcms.card.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.card.manage.entity.PatientBaseInfo;
import com.ewcms.card.manage.entity.PracticeCard;
import com.ewcms.card.manage.entity.PracticeCardHistory;
import com.ewcms.card.manage.entity.PracticeCardJournal;
import com.ewcms.card.manage.entity.PracticeCardJournalOperate;
import com.ewcms.card.manage.entity.PracticeCardOperate;
import com.ewcms.card.manage.entity.PracticeCardStatus;
import com.ewcms.common.exception.BaseException;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;

/**
 *@author zhoudongchu
 */
@Service
public class PracticeCardService extends BaseService<PracticeCard, Long> {
	
	@Autowired
	private PatientBaseInfoService patientBaseInfoService;
	@Autowired
	private PracticeCardJournalService practiceCardJournalService;

	/**
	 * 发诊疗卡
	 * 
	 * @param m 诊疗卡对象
	 * @param userId 操作员ID
	 * @return 诊疗卡
	 */
	public PracticeCard saveDeposit(PracticeCard m, Long userId) {
		String certificateNo = m.getPatientBaseInfo().getCertificateNo();
		if (EmptyUtil.isNull(certificateNo)) throw new BaseException("患者基本信息不存在");
		
		PracticeCardJournal practiceCardJournal = new PracticeCardJournal(m.getBalance(),"发诊疗卡充值",userId,PracticeCardJournalOperate.deposit);	
		m.getPracticeCardJournals().add(practiceCardJournal);
		PracticeCardHistory practiceCardHistory = new PracticeCardHistory("发诊疗卡",userId,PracticeCardOperate.distribute);
		m.getPracticeCardHistorys().add(practiceCardHistory);
		PracticeCard practiceCard = null;
//		TODO 除了使用certificateNo（证件号码）外，还可以使用姓名+电话号码等形式判断信息存在与否和查询患者基本信息
		PatientBaseInfo dbPatientBaseInfo = patientBaseInfoService.findByCertificateNo(certificateNo);
		
		if (EmptyUtil.isNull(dbPatientBaseInfo)){//数据库找不到患者信息

			practiceCard = super.save(m);//新建诊疗卡和患者信息
			
			//根据患者Id生产患者唯一索引号patientId
			m.getPatientBaseInfo().setPatientId(String.format("%0"+ PatientBaseInfo.patientIdlength +"d", practiceCard.getPatientBaseInfo().getId()));
			
			return super.update(m);
		}else{
			PatientBaseInfo currentPatientBaseInfo =m.getPatientBaseInfo(); 
			m.setPatientBaseInfo(dbPatientBaseInfo);
			practiceCard = super.save(m);//新建诊疗卡信息
			
			currentPatientBaseInfo.setId(dbPatientBaseInfo.getId());
			currentPatientBaseInfo.setPatientId(dbPatientBaseInfo.getPatientId());
			practiceCard.setPatientBaseInfo(currentPatientBaseInfo);
			
			return super.update(practiceCard);//更新患者信息
		}
	}
	/**
	 * 挂失诊疗卡
	 * 
	 * @param practiceCardIds 诊疗卡集合
	 * @param userId 操作员ID
	 * @return 
	 */	
    public void loss(List<Long> practiceCardIds, Long userId){
    	PracticeCard practiceCard = null;
    	PracticeCardHistory practiceCardHistory = null;
    	for(Long practiceCardId:practiceCardIds){
    		practiceCard = findOne(practiceCardId);
    		if(practiceCard.getStatus() == PracticeCardStatus.normal){//正常卡才可以挂失
	    		practiceCard.setStatus(PracticeCardStatus.loss);
	    		practiceCardHistory = new PracticeCardHistory("诊疗卡挂失",userId,PracticeCardOperate.loss);
	    		practiceCard.getPracticeCardHistorys().add(practiceCardHistory);
	    		super.update(practiceCard);
    		}
    	}
    }
    
	/**
	 * 取消挂失诊疗卡
	 * 
	 * @param practiceCardIds 诊疗卡集合
	 * @param userId 操作员ID
	 * @return 
	 */	
    public void cancelLoss(List<Long> practiceCardIds, Long userId){
    	PracticeCard practiceCard = null;
    	PracticeCardHistory practiceCardHistory = null;
    	for(Long practiceCardId:practiceCardIds){
    		practiceCard = findOne(practiceCardId);
    		if(practiceCard.getStatus() == PracticeCardStatus.loss){//挂失卡才可以取消挂失
	    		practiceCard.setStatus(PracticeCardStatus.normal);
	    		practiceCardHistory = new PracticeCardHistory("诊疗卡取消挂失",userId,PracticeCardOperate.cancelloss);
	    		practiceCard.getPracticeCardHistorys().add(practiceCardHistory);
	    		super.update(practiceCard);
    		}
    	}
    }    
}
