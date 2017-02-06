package com.ewcms.empi.card.manage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.exception.BaseException;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.entity.PracticeCardDepositOperate;
import com.ewcms.empi.card.manage.entity.PracticeCardHistory;
import com.ewcms.empi.card.manage.entity.PracticeCardJournal;
import com.ewcms.empi.card.manage.entity.PracticeCardJournalOperate;
import com.ewcms.empi.card.manage.entity.PracticeCardOperate;
import com.ewcms.empi.card.manage.entity.PracticeCardStatus;
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
	@Autowired
	private PracticeCardJournalService practiceCardJournalService;

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
	public PracticeCard distribute(PracticeCard m, Long userId) {
		PracticeCard dbPracticeCard = getPracticeCardRepository().findByPracticeNo(m.getPracticeNo());
		if(EmptyUtil.isNotNull(dbPracticeCard))throw new BaseException("诊疗卡号已存在");
		String certificateNo = m.getPatientBaseInfo().getCertificateNo();
		if (EmptyUtil.isNull(certificateNo)) throw new BaseException("患者基本信息不存在");
		if(m.getDepositOperate() == PracticeCardDepositOperate.redeposit)throw new BaseException("发诊疗卡不能退押金");
		if(m.getDepositOperate() == PracticeCardDepositOperate.nodeposit)m.setDeposit(0D);//不收押金
		
		PracticeCardJournal practiceCardJournal = new PracticeCardJournal(m.getBalance(),"发诊疗卡充值",userId,PracticeCardJournalOperate.recharge);	
		m.getPracticeCardJournals().add(practiceCardJournal);
		PracticeCardHistory practiceCardHistory = new PracticeCardHistory("发诊疗卡",userId,PracticeCardOperate.distribute,PracticeCardStatus.normal);
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
			//TODO 如果页面未读取患者信息，那么页面新的患者信息会覆盖掉原有的患者信息
			return super.update(practiceCard);//更新患者信息
		}
	}
	/**
	 * 诊疗卡挂失
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
	    		practiceCardHistory = new PracticeCardHistory("诊疗卡挂失",userId,PracticeCardOperate.loss,PracticeCardStatus.loss);
	    		practiceCard.getPracticeCardHistorys().add(practiceCardHistory);
	    		super.update(practiceCard);
    		}
    	}
    }
    
	/**
	 * 诊疗卡取消挂失
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
	    		practiceCardHistory = new PracticeCardHistory("诊疗卡取消挂失",userId,PracticeCardOperate.cancelloss,PracticeCardStatus.normal);
	    		practiceCard.getPracticeCardHistorys().add(practiceCardHistory);
	    		super.update(practiceCard);
    		}
    	}
    }
    
	/**
	 * 诊疗卡销户
	 * 
	 * @param practiceCardIds 诊疗卡集合
	 * @param userId 操作员ID
	 * @return 
	 */	
    public void close(List<Long> practiceCardIds, Long userId){
    	PracticeCard practiceCard = null;
    	PracticeCardHistory practiceCardHistory = null;
    	for(Long practiceCardId:practiceCardIds){
    		practiceCard = findOne(practiceCardId);
    		if(practiceCard.getStatus() == PracticeCardStatus.normal||practiceCard.getStatus() == PracticeCardStatus.loss){//规定正常卡和挂失卡才可以销户
	    		practiceCard.setStatus(PracticeCardStatus.close);
	    		practiceCardHistory = new PracticeCardHistory("诊疗卡销户",userId,PracticeCardOperate.close,PracticeCardStatus.close);
	    		practiceCard.getPracticeCardHistorys().add(practiceCardHistory);
	    		super.update(practiceCard);
    		}
    	}
    } 
    
	/**
	 * 诊疗卡作废
	 * 
	 * @param practiceCardIds 诊疗卡集合
	 * @param userId 操作员ID
	 * @return 
	 */	
    public Map<String, Object> invalid(Long practiceCardId, Long userId){
    	if(practiceCardId == null)throw new BaseException("未输入作废卡信息");
    	PracticeCard practiceCard = findOne(practiceCardId);
    	if(practiceCard == null)throw new BaseException("未找到作废卡信息");
    	if(practiceCard.getStatus() == PracticeCardStatus.invalid)throw new BaseException("输入的卡已是作废卡");
    	Double reBalance = 0D,reDeposit = 0D;
		//TODO 返还余额未结算看病费用
		reBalance = practiceCard.getBalance();
    	if(reBalance < 0)throw new BaseException("诊疗卡欠费，不能作废处理");
		if(practiceCard.getDepositOperate().equals(PracticeCardDepositOperate.paydeposit)){//收取押金做退押金处理
			practiceCard.setDepositOperate(PracticeCardDepositOperate.redeposit);
			reDeposit = practiceCard.getDeposit();
		}  

    	practiceCard.setStatus(PracticeCardStatus.invalid);
    	practiceCard.setBalance(0D);
    	PracticeCardJournal practiceCardJournal = new PracticeCardJournal(-reBalance,"诊疗卡作废时的退费",userId,PracticeCardJournalOperate.refund);
    	practiceCard.getPracticeCardJournals().add(practiceCardJournal);
    	
    	PracticeCardHistory practiceCardHistory = new PracticeCardHistory("诊疗卡作废",userId,PracticeCardOperate.invalid,PracticeCardStatus.invalid);
    	practiceCard.getPracticeCardHistorys().add(practiceCardHistory);
    	super.update(practiceCard);
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>(4);
    	StringBuffer strMessage = new StringBuffer();
    	strMessage.append("诊疗卡作废成功");
    	resultMap.put("reDeposit", reDeposit);
    	strMessage.append("，应退押金：" + reDeposit + "元");
    	strMessage.append("，应退余额：" + reBalance + "元");
    	resultMap.put("reBalance", reBalance);
    	resultMap.put("message", strMessage.toString());
    	resultMap.put("success", Boolean.TRUE);
    	return resultMap;
		
    }
    
	/**
	 * 诊疗卡合并
	 * 
	 * @param practiceCardIds 要合并的诊疗卡集合
	 * @param practiceNo 合并后的诊疗卡号
	 * @param userId 操作员ID
	 * @return 
	 */	
    public Map<String, Object> combine(List<Long> practiceCardIds,String practiceNo, Long userId){
    	PracticeCard practiceCard = null;
    	PracticeCard dbPracticeCard = findByPracticeNo(practiceNo);
    	if(EmptyUtil.isNotNull(dbPracticeCard) && dbPracticeCard.getStatus().equals(PracticeCardStatus.invalid))throw new BaseException("合并后的启用卡是作废卡");
    	Long patientBaseInfoId = null;
    	for(Long practiceCardId:practiceCardIds){//判断要合并的诊疗卡是否同一患者
    		practiceCard = findOne(practiceCardId);
    		if(practiceCard.getStatus().equals(PracticeCardStatus.invalid))throw new BaseException("作废卡不能进行合并");
    		if(EmptyUtil.isNotNull(patientBaseInfoId)){
    			if(!patientBaseInfoId.equals(practiceCard.getPatientBaseInfo().getId()))throw new BaseException("所选合并诊疗卡不是同一患者");
    		}
    		
    		patientBaseInfoId = practiceCard.getPatientBaseInfo().getId();
    		
    		if(EmptyUtil.isNotNull(dbPracticeCard) && !dbPracticeCard.getPatientBaseInfo().getId().equals(patientBaseInfoId))throw new BaseException("合并后的诊疗卡与所要合并的卡不是同一患者");
    	}
    	
    	Double reDeposit = 0D;
    	Double balance = 0D;
    	Integer backCard = 0;
    	PracticeCardHistory practiceCardHistory = null;
    	for(Long practiceCardId:practiceCardIds){//对合并的卡做合并处理操作，退还押金，退余额，作废
    		practiceCard = findOne(practiceCardId);
    		if(practiceNo.equals(practiceCard.getPracticeNo()))continue;//合并后的诊疗卡不操作
    		if(practiceCard.getDepositOperate().equals(PracticeCardDepositOperate.paydeposit)){//收取押金做退押金处理
    			practiceCard.setDepositOperate(PracticeCardDepositOperate.redeposit);
    			reDeposit += practiceCard.getDeposit();
    		}
    		//TODO 余额处理未结算看病费用
    		balance += practiceCard.getBalance();
    		backCard++;
	    	practiceCard.setStatus(PracticeCardStatus.invalid);
	    	PracticeCardJournal practiceCardJournal = new PracticeCardJournal(-practiceCard.getBalance(),"合并诊疗卡退费",userId,PracticeCardJournalOperate.refund);
	    	practiceCard.getPracticeCardJournals().add(practiceCardJournal);
	    	practiceCardHistory = new PracticeCardHistory("诊疗卡合并",userId,PracticeCardOperate.combine,PracticeCardStatus.invalid);
	    	practiceCard.getPracticeCardHistorys().add(practiceCardHistory);
	    	practiceCard.setBalance(0D); 	
	    	super.update(practiceCard);
    	}
    	
    	if(EmptyUtil.isNull(dbPracticeCard)){
    		dbPracticeCard = new PracticeCard();
    		dbPracticeCard.setBalance(balance);
    		dbPracticeCard.setDeposit(0D);
    		dbPracticeCard.setDepositOperate(PracticeCardDepositOperate.nodeposit);
    		dbPracticeCard.setPatientBaseInfo(patientBaseInfoService.findOne(patientBaseInfoId));
    		dbPracticeCard.setPracticeNo(practiceNo);
    		dbPracticeCard.setStatus(PracticeCardStatus.normal);
	    	PracticeCardJournal practiceCardJournal = new PracticeCardJournal(balance,"合并诊疗卡充值",userId,PracticeCardJournalOperate.recharge);
	    	dbPracticeCard.getPracticeCardJournals().add(practiceCardJournal);
	    	practiceCardHistory = new PracticeCardHistory("诊疗卡合并",userId,PracticeCardOperate.combine,PracticeCardStatus.invalid);
	    	dbPracticeCard.getPracticeCardHistorys().add(practiceCardHistory);    
	    	practiceCard = super.save(dbPracticeCard);
	    	
    	}else{
    		dbPracticeCard.setBalance(balance+dbPracticeCard.getBalance());
	    	PracticeCardJournal practiceCardJournal = new PracticeCardJournal(balance,"合并诊疗卡充值",userId,PracticeCardJournalOperate.recharge);
	    	dbPracticeCard.getPracticeCardJournals().add(practiceCardJournal);
	    	practiceCard = super.update(dbPracticeCard);
    	}
    	Map<String, Object> resultMap = new HashMap<String, Object>(5);
    	resultMap.put("combineCardId", practiceCard.getId());
    	resultMap.put("reDeposit", reDeposit);
    	resultMap.put("backCard", backCard);
    	resultMap.put("message", "诊疗卡合并成功");
    	resultMap.put("success", Boolean.TRUE);
    	return resultMap;
    }   
}
