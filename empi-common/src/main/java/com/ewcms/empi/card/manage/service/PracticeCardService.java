package com.ewcms.empi.card.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.exception.BaseException;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.entity.MessageLog;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.entity.PracticeCard;
import com.ewcms.empi.card.manage.entity.PracticeCardIndex;
import com.ewcms.empi.card.manage.entity.PracticeCardIndexHistory;
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
	@Autowired
	private PracticeCardIndexHistoryService practiceCardIndexHistoryService;
	
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
		List<PatientBaseInfo> noRepeatPatientBaseInfoList = patientBaseInfoService.findNoRepeatByMatchRule(matchRuleList);//根据匹配规则查找不重复的患者信息集合
		List<PatientBaseInfo> repeatPatientBaseInfoList = patientBaseInfoService.findRepeatByMatchRule(matchRuleList);//根据匹配字段获取重复患者信息集
		List<PatientBaseInfo> matchPatientBaseInfoList;
		List<PracticeCard> practiceCardList;
		PracticeCardIndex practiceCardIndex;
		PracticeCardIndexHistory practiceCardIndexHistory;
		Integer patientIdLength = parameterSetService.findPatientIdVariableValue();
		String patientId;
		
		if(EmptyUtil.isCollectionNotEmpty(noRepeatPatientBaseInfoList)){
			for(PatientBaseInfo patientBaseInfo:noRepeatPatientBaseInfoList){
				matchPatientBaseInfoList = patientBaseInfoService.match(patientBaseInfo);//匹配查询患者
				if(EmptyUtil.isCollectionNotEmpty(matchPatientBaseInfoList)){
					patientId = String.format("%0" + patientIdLength + "d", matchPatientBaseInfoList.get(0).getId());
					for(PatientBaseInfo dbPatientBaseInfo:matchPatientBaseInfoList){//循环更新患者卡的唯一索引号
						practiceCardList = dbPatientBaseInfo.getPracticeCards();
						if(EmptyUtil.isCollectionNotEmpty(practiceCardList)){
							for(PracticeCard practiceCard:practiceCardList){
								practiceCardIndex = practiceCardIndexService.findOne(practiceCard.getPracticeNo());
								if(EmptyUtil.isNotNull(practiceCardIndex) && !practiceCardIndex.getPatientId().equals(patientId)){//如果卡的唯一索引号与当前不同就进行更新
									practiceCardIndexHistory = new PracticeCardIndexHistory();
									practiceCardIndexHistory.setPracticeNo(practiceCard.getPracticeNo());
									practiceCardIndexHistory.setBeforePatientId(practiceCardIndex.getPatientId());
									practiceCardIndexHistory.setAfterPatientId(patientId);
									practiceCardIndexHistory.setRemark("自动合并诊疗卡");
									
									practiceCardIndex.setPatientId(patientId);
									practiceCardIndex.setPushed(Boolean.TRUE);//患者索引已更新，设置需要进行信息推送
									practiceCardIndexService.update(practiceCardIndex);
									practiceCardIndexHistoryService.save(practiceCardIndexHistory);
								}
							}
						}
					}
				}
			}
		}
		
		

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
									
									practiceCardIndexHistory = new PracticeCardIndexHistory();
									practiceCardIndexHistory.setPracticeNo(practiceCard.getPracticeNo());
									practiceCardIndexHistory.setBeforePatientId(practiceCardIndex.getPatientId());
									practiceCardIndexHistory.setAfterPatientId(patientId);
									practiceCardIndexHistory.setRemark("自动合并诊疗卡");
									
									practiceCardIndex.setPatientId(patientId);
									practiceCardIndex.setPushed(Boolean.TRUE);//患者索引已更新，设置需要进行信息推送
									practiceCardIndexService.update(practiceCardIndex);
									practiceCardIndexHistoryService.save(practiceCardIndexHistory);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 根据诊疗卡号和患者信息注册一个患者
	 * 
	 * @param practiceNo 诊疗卡号
	 * @param patientBaseInfo 患者信息
	 * @return PracticeCard 诊疗卡对象
	 */	
	@MessageLogFilter(modelObjectIndex = 2)
	public PracticeCard register(String practiceNo,PatientBaseInfo patientBaseInfo,MessageLog messageLog) {
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
		
		PracticeCardIndexHistory practiceCardIndexHistory = new PracticeCardIndexHistory();
		practiceCardIndexHistory.setPracticeNo(practiceNo);
		practiceCardIndexHistory.setAfterPatientId(patientIdStr);
		practiceCardIndexHistory.setRemark("注册诊疗卡");
		practiceCardIndexHistoryService.save(practiceCardIndexHistory);
		return practiceCard;
	}
}
