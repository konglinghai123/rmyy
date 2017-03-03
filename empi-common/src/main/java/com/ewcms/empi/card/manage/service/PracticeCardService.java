package com.ewcms.empi.card.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.exception.BaseException;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.entity.PracticeCardIndex;
import com.ewcms.empi.card.manage.repository.PracticeCardRepository;
import com.ewcms.empi.system.service.ParameterSetService;

/**
 *@author zhoudongchu
 */
@Service
public class PracticeCardService extends BaseService<PracticeCard, Long> {
	@Autowired
	private PatientBaseInfoService patientBaseInfoService;
	@Autowired
	private MatchRuleService matchRuleService;
	@Autowired
	private ParameterSetService parameterSetService;
	@Autowired
	private PracticeCardIndexService practiceCardIndexService;
	
	private PracticeCardRepository getPracticeCardRepository() {
		return (PracticeCardRepository) baseRepository;
	}
	public PracticeCard findByPracticeNoAndDeleted(String practiceNo,Boolean deleted){
		return getPracticeCardRepository().findByPracticeNoAndDeleted(practiceNo,deleted);
	} 
	/**
	 * 根据匹配规则重新建立诊疗卡索引
	 * 
	 * @param 
	 * @return
	 */
	public void autoCombine(){
		List<MatchRule> matchRuleList = matchRuleService.findMatchRuleByMatched();//获取需要匹配的字段
		List<PatientBaseInfo> repeatPatientBaseInfoList = patientBaseInfoService.findByMatchRule(matchRuleList);//根据匹配字段获取重复患者信息集
		List<PatientBaseInfo> matchPatientBaseInfoList;
		List<PracticeCard> practiceCardList;
		PracticeCardIndex practiceCardIndex;
		Integer patientIdLength = parameterSetService.findPatientIdVariableValue();
		String patientId;
		if(EmptyUtil.isCollectionNotEmpty(repeatPatientBaseInfoList)){
			for(PatientBaseInfo patientBaseInfo:repeatPatientBaseInfoList){
				matchPatientBaseInfoList = patientBaseInfoService.match(patientBaseInfo);//匹配查询重复的患者
				if(EmptyUtil.isCollectionNotEmpty(matchPatientBaseInfoList)){
					patientId = String.format("%0" + patientIdLength + "d", matchPatientBaseInfoList.get(0).getId());//取第一条的患者 信息作为唯一索引号
					for(PatientBaseInfo dbPatientBaseInfo:matchPatientBaseInfoList){//循环更新患者卡的唯一索引号
						practiceCardList = dbPatientBaseInfo.getPracticeCards();
						if(EmptyUtil.isCollectionNotEmpty(practiceCardList)){
							for(PracticeCard practiceCard:practiceCardList){
								practiceCardIndex = practiceCardIndexService.findOne(practiceCard.getPracticeNo());
								if(EmptyUtil.isNotNull(practiceCardIndex) && !practiceCardIndex.getPatientId().equals(patientId)){//如果卡的唯一索引号与当前不同就进行更新
									practiceCardIndex.setPatientId(patientId);
									practiceCardIndex.setPushed(Boolean.TRUE);//患者索引已更新，设置需要进行信息推送
									practiceCardIndexService.update(practiceCardIndex);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 发诊疗卡
	 * 
	 * @param m 诊疗卡对象
	 * @param userId 操作员ID
	 * @return 诊疗卡
	 */
	public PracticeCard distribute(PracticeCard m) {
		PracticeCard dbPracticeCard = findByPracticeNoAndDeleted(m.getPracticeNo(),Boolean.FALSE);
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
	
	public PracticeCard register(String practiceNo,PatientBaseInfo patientBaseInfo) {
		PracticeCard dbPracticeCard = findByPracticeNoAndDeleted(practiceNo,Boolean.FALSE);
		if(EmptyUtil.isNotNull(dbPracticeCard))throw new BaseException("诊疗卡号已存在");
		List<PatientBaseInfo> repeatPatientBaseInfoList = patientBaseInfoService.match(patientBaseInfo);
		Integer patientIdLength = parameterSetService.findPatientIdVariableValue();

		if(EmptyUtil.isCollectionNotEmpty(repeatPatientBaseInfoList)){//数据库存在匹配的患者信息
			PatientBaseInfo dbPatientBaseInfo = repeatPatientBaseInfoList.get(0);
			patientBaseInfo.setId(dbPatientBaseInfo.getId());
			patientBaseInfo = patientBaseInfoService.update(patientBaseInfo);//更新患者信息
		}else{
			patientBaseInfo = patientBaseInfoService.saveAndFlush(patientBaseInfo);//新增患者信息
		}
		PracticeCard practiceCard = new PracticeCard();
		practiceCard.setPracticeNo(practiceNo);
		practiceCard.setPatientBaseInfo(patientBaseInfo);
		practiceCard = super.save(practiceCard); //新建诊疗卡
		
		String patientIdStr = String.format("%0" + patientIdLength + "d", patientBaseInfo.getId());
		practiceCard.getPatientBaseInfo().setPatientId(patientIdStr);
		
		PracticeCardIndex practiceCardIndex = new PracticeCardIndex();
		
		practiceCardIndex.setId(practiceNo);
		practiceCardIndex.setPatientBaseInfoId(patientBaseInfo.getId());
		practiceCardIndex.setPatientId(patientIdStr);
		
		practiceCardIndexService.save(practiceCardIndex);//新建患者唯一索引
		return practiceCard;
	}
}
