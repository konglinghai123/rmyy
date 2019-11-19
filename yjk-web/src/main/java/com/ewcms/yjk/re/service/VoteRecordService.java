package com.ewcms.yjk.re.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.validate.AjaxResponse;
import com.ewcms.yjk.YjkConstants;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.entity.VoteTypeEnum;
import com.ewcms.yjk.re.model.VoteMonitor;
import com.ewcms.yjk.re.repository.VoteRecordRepository;

import com.ewcms.yjk.sb.service.DrugFormService;

import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;

import com.ewcms.yjk.zd.commonname.service.CommonNameService;
import com.ewcms.yjk.zd.commonname.service.HospitalContentsService;

@Service
public class VoteRecordService extends BaseService<VoteRecord, Long> {
	@Autowired
	private DrugFormService drugFormService;
	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private ReviewProcessService reviewProcessService;
	@Autowired
	private VoteResultService voteResultService;
	@Autowired
	private CommonNameService commonNameService;
	@Autowired
	private HospitalContentsService hospitalContentsService;

	private VoteRecordRepository getVoteRecordRepository() {
		return (VoteRecordRepository) baseRepository;
	}

	public Boolean expertSubmitCurrentReview(Long userId, Long reviewProcessId) {
		if (getVoteRecordRepository()
				.countByUserIdAndReviewProcessIdAndSubmittedTrue(userId,
						reviewProcessId) == 0) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}

	public VoteRecord findByUserIdAndReviewMainIdAndDrugFormIdAndReviewProcessIdAndSubmittedTrue(
			Long userId, Long reviewMainId, Long drugFormId,
			Long reviewProcessId) {
		return getVoteRecordRepository()
				.findByUserIdAndReviewMainIdAndDrugFormIdAndReviewProcessIdAndSubmittedTrue(
						userId, reviewMainId, drugFormId, reviewProcessId);
	}

	/**
	 * 启动专家新增通用名投票
	 * 
	 */
	public String expertStartVoteAddCommonName(Long userId,
			Long currentReviewProcessId) {
		//initDrugFormVoteResult(currentReviewProcessId, "新增通用名");
		return initUserDrugFormVoteRecord(userId, currentReviewProcessId);
	}

	/**
	 * 启动专家新增剂型规格的投票
	 * 
	 */
	public String expertStartVoteAddSpecificationsAndPill(Long userId,
			Long currentReviewProcessId) {
		//initDrugFormVoteResult(currentReviewProcessId, "新增规格/剂型");
		return initUserDrugFormVoteRecord(userId, currentReviewProcessId);
	}

	private  String initUserDrugFormVoteRecord(Long userId,Long currentReviewProcessId) {
		ReviewMain reviewMainEnable = reviewMainService.findByEnabledTrue();
		if (reviewMainEnable == null)
			return "评审还未启动！";
		
		if (getVoteRecordRepository().countByUserIdAndReviewProcessId(userId,
				currentReviewProcessId).longValue() == 0) {// 用户初次进入投票，初始化需要投票的申报新曾通用名的药品
			List<VoteResult> voteResultList = voteResultService
					.findByReviewProcessId(currentReviewProcessId);
			for (VoteResult voteResult : voteResultList) {
				VoteRecord vo = new VoteRecord();
				vo.setSubmitted(Boolean.FALSE);
				vo.setUserId(userId);
				vo.setReviewMainId(reviewMainEnable.getId());
				vo.setReviewProcessId(voteResult.getReviewProcessId());
				vo.setDrugForm(voteResult.getDrugForm());
				//vo.setCommonNameContents(voteResult.getCommonNameContents());
				vo.setOrganizationNames(voteResult.getOrganizationNames());
				getVoteRecordRepository().save(vo);
			}
		}

		return currentReviewProcessId.toString();
	}
	

	
	/**
	 * 启动专家通用名厂家遴选投票
	 * 
	 */
	public String expertStartVoteAddCommonNameManufacturer(Long userId,
			Long currentReviewProcessId) {
		//initCommonNameManufacturerVoteResult(currentReviewProcessId, "新增通用名");
		return initUserCommonNameManufacturerVoteRecord(userId, currentReviewProcessId);
	}

	/**
	 * 启动专家新增规格/剂型厂家遴选投票
	 * 
	 */
	public String expertStartVoteAddSpecificationsAndPillManufacturer(Long userId,
			Long currentReviewProcessId) {
		//initCommonNameManufacturerVoteResult(currentReviewProcessId, "新增规格/剂型");
		return initUserCommonNameManufacturerVoteRecord(userId, currentReviewProcessId);
	}
	

	private  String initUserCommonNameManufacturerVoteRecord(Long userId,Long currentReviewProcessId) {
		ReviewMain reviewMainEnable = reviewMainService.findByEnabledTrue();
		if (reviewMainEnable == null)
			return "评审还未启动！";		

		if (getVoteRecordRepository().countByUserIdAndReviewProcessId(userId,
				currentReviewProcessId).longValue() == 0) {// 用户初次进入投票的专家，初始化需要投票的申报新曾通用名的大总目录厂家药品
			List<VoteResult> voteResultList = voteResultService
					.findByReviewProcessId(currentReviewProcessId);
			for (VoteResult voteResult : voteResultList) {
				VoteRecord vo = new VoteRecord();
				vo.setSubmitted(Boolean.FALSE);
				vo.setUserId(userId);
				vo.setReviewMainId(reviewMainEnable.getId());
				vo.setReviewProcessId(voteResult.getReviewProcessId());
				vo.setDrugForm(voteResult.getDrugForm());
				vo.setOrganizationNames(voteResult.getOrganizationNames());
				vo.setCommonNameContents(voteResult.getCommonNameContents());
				getVoteRecordRepository().save(vo);
			}
		}

		return currentReviewProcessId.toString();
	}
	
	/**
	 * 保存专家投票记录集
	 * 
	 */
	public void saveVote(Long userId, List<String> selections) {
		for (String voteReult : selections) {
			String[] voteResultArr = voteReult.split("@");
			if (voteResultArr.length == 2) {
				VoteRecord vo = getVoteRecordRepository().findOne(
						Long.parseLong(voteResultArr[0]));
				if (vo != null && vo.getUserId().equals(userId)
						&& !vo.getSubmitted()) {
					vo.setVoteTypeEnum(VoteTypeEnum.valueOf(voteResultArr[1]));
					super.save(vo);
				}
			}
		}
	}

	/**
	 * 提交专家投票记录集
	 * 
	 */
	public void submitVote(Long userId, List<String> selections) {
		for (String voteReult : selections) {
			String[] voteResultArr = voteReult.split("@");
			if (voteResultArr.length == 2) {
				VoteRecord vo = getVoteRecordRepository().findOne(
						Long.parseLong(voteResultArr[0]));
				if (vo != null && vo.getUserId().equals(userId)
						&& !vo.getSubmitted()) {
					VoteTypeEnum voteTypeEnum = VoteTypeEnum
							.valueOf(voteResultArr[1]);
					vo.setVoteTypeEnum(voteTypeEnum);
					vo.setSubmitted(Boolean.TRUE);
					vo.setSubmittDate(new Date(Calendar.getInstance().getTime()
							.getTime()));
					super.save(vo);
					VoteResult voteResult = null;
					ReviewProcess reviewProcess = reviewProcessService.findOne(vo.getReviewProcessId());
					String ruleName = reviewProcess.getReviewBaseRule().getRuleName();
					if(ruleName.equals(YjkConstants.ACN)||ruleName.equals(YjkConstants.ASAP)) {
						voteResult = voteResultService.findByDrugFormIdAndReviewProcessId(vo.getDrugForm().getId(), vo.getReviewProcessId());
					} else 	if(ruleName.equals(YjkConstants.ACNM)||ruleName.equals(YjkConstants.ASAPM)) {
						voteResult = voteResultService.findByCommonNameContentsIdAndReviewProcessId(vo.getCommonNameContents().getId(), vo.getReviewProcessId());
					}
					
					if (voteResult != null
							&& !voteResult.getAffirmVoteResulted()) {
						if (voteTypeEnum.equals(VoteTypeEnum.pass)) {
							voteResult.setPassSum(voteResult.getPassSum() + 1);
						} else if (voteTypeEnum.equals(VoteTypeEnum.oppose)) {
							voteResult.setOpposeSum(voteResult.getOpposeSum() + 1);
						} else if (voteTypeEnum.equals(VoteTypeEnum.abstain)) {
							voteResult.setAbstainSum(voteResult.getAbstainSum() + 1);
						}
						voteResultService.save(voteResult);
					}
				}
			}
		}
	}

	/**
	 * 查询已投票专家编号集合
	 * 
	 * @param reviewMainId
	 * @param reviewProcessId
	 * @return
	 */
	public List<Long> findSubmittedUserIds(Long reviewMainId, Long reviewProcessId) {
		return getVoteRecordRepository().findSubmittedUserIds(reviewMainId,reviewProcessId);
	}

	/**
	 * 查询中止投票记录集
	 * 
	 * @param userId
	 * @param reviewMainId
	 * @param reviewProcessId
	 * @return
	 */
	public List<VoteRecord> findByUserIdAndReviewMainIdAndReviewProcessIdAndDrugFormIdIsNullAndCommonNameContentsIdIsNull(
			Long userId, Long reviewMainId, Long reviewProcessId) {
		return getVoteRecordRepository()
				.findByUserIdAndReviewMainIdAndReviewProcessIdAndDrugFormIdIsNullAndCommonNameContentsIdIsNull(
						userId, reviewMainId, reviewProcessId);
	}

	/**
	 * 中止投票（由管理人员操作），在VoteRecord对象中产生一条drugForm和commonNameContents都为空的一条记录
	 * 
	 * @param userId
	 */
	public AjaxResponse giveUp(Long userId) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		String message = "中止投票操作成功！";
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) {
			message = "评审未开启，不能操作！";
			ajaxResponse.setSuccess(Boolean.FALSE);
		} else {
			Long reviewMainId = reviewMain.getId();
			ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			if (reviewProcess == null) {
				message = "没有流程可使用或当前流程已结束！";
				ajaxResponse.setSuccess(Boolean.FALSE);
			} else {
				Long reviewProcessId = reviewProcess.getId();
				Long countSigned = getVoteRecordRepository().countByUserIdAndReviewMainIdAndReviewProcessIdAndSubmittedTrueAndSignedTrue(userId, reviewMainId, reviewProcessId);
				if (countSigned > 0) {
					message = "本轮用户已签字，不能中止投票！";
					ajaxResponse.setSuccess(Boolean.FALSE);
				} else {
					List<VoteRecord> voteRecords = findByUserIdAndReviewMainIdAndReviewProcessIdAndDrugFormIdIsNullAndCommonNameContentsIdIsNull(
							userId, reviewMainId, reviewProcessId);
					if (EmptyUtil.isCollectionEmpty(voteRecords)) {
						//先删除
						getVoteRecordRepository().deleteGvieUpVoteRecord(userId, reviewMainId, reviewProcessId);
						
						VoteRecord voteRecord = new VoteRecord();
						voteRecord.setUserId(userId);
						voteRecord.setReviewMainId(reviewMainId);
						voteRecord.setReviewProcessId(reviewProcessId);
						voteRecord.setVoteTypeEnum(VoteTypeEnum.abstain);
						voteRecord.setSigned(Boolean.TRUE);
						voteRecord.setSubmitted(Boolean.TRUE);
						voteRecord.setSubmittDate(new Date(Calendar.getInstance().getTime().getTime()));
						super.save(voteRecord);
						
						
					}
				}
			}
		}
		ajaxResponse.setMessage(message);
		return ajaxResponse;
	}

	
	
	public AjaxResponse sign(Long userId) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		String message = "签字操作成功！";
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null) {
			message = "评审未开启，不能操作！";
			ajaxResponse.setSuccess(Boolean.FALSE);
		} else {
			Long reviewMainId = reviewMain.getId();
			ReviewProcess reviewProcess = reviewProcessService.findCurrentReviewProcess(reviewMainId);
			if (reviewProcess == null) {
				message = "没有流程可使用或当前流程已结束！";
				ajaxResponse.setSuccess(Boolean.FALSE);
			} else {
				Long reviewProcessId = reviewProcess.getId();
				Long noSigned = getVoteRecordRepository().countByUserIdAndReviewMainIdAndReviewProcessIdAndSignedFalse(userId, reviewMainId, reviewProcessId);
				if (noSigned.longValue() > 0) {
					getVoteRecordRepository().updateSigned(userId, reviewMainId,reviewProcessId);
				}
			}
		}
		ajaxResponse.setMessage(message);
		return ajaxResponse;
	}
	
	/**
	 * 查询用户投票记录
	 * 
	 * @param userId
	 * @param reviewMainId
	 * @param reviewProcessId
	 * @return
	 */
	public List<VoteRecord> findByUserIdAndReviewMainIdAndReviewProcessIdAndDrugFormIsNotNull(Long userId, Long reviewMainId, Long reviewProcessId){
		return getVoteRecordRepository().findByUserIdAndReviewMainIdAndReviewProcessIdAndDrugFormIsNotNull(userId, reviewMainId, reviewProcessId);
	}
	
	public List<VoteMonitor> findVoteResultMonitor(List<Long> userIds, Long reviewMainId, Long reviewProcessId){
		return getVoteRecordRepository().findVoteResultMonitor(userIds, reviewMainId, reviewProcessId);
	}
	
	public List<VoteMonitor> findVoteUserMonitorDrugForm(Long reviewMainId, Long reviewProcessId, Long drugFormId){
		return getVoteRecordRepository().findVoteUserMonitorDrugForm(reviewMainId, reviewProcessId, drugFormId);
	}
	
	public List<VoteMonitor> findVoteUserMonitorCommonNameContents(Long reviewMainId, Long reviewProcessId, Long commonNameContentsId){
		return getVoteRecordRepository().findVoteUserMonitorCommonNameContents(reviewMainId, reviewProcessId, commonNameContentsId);
	}
	
	public Long findSubmittedAndSinged(Long reviewMainId, Long reviewProcessId) {
		return getVoteRecordRepository().findSubmittedAndSinged(reviewMainId, reviewProcessId);
	}
	
	public String statisticalNotes(Long userId, Long reviewProcessId) {
		Long recordNumber = getVoteRecordRepository().countByUserIdAndReviewProcessId(userId, reviewProcessId);//投票的药品数
		Long hRecordNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndDrugFormCommonNameContentsCommonDrugCategory(userId, reviewProcessId, DrugCategoryEnum.H);//投票的西药数量
		Long zRecordNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndDrugFormCommonNameContentsCommonDrugCategory(userId, reviewProcessId, DrugCategoryEnum.Z);//投票的中成药数量
		
		Long passNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndVoteTypeEnum(userId, reviewProcessId, VoteTypeEnum.pass);
		Long hPassNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndVoteTypeEnumAndDrugFormCommonNameContentsCommonDrugCategory(userId, reviewProcessId, VoteTypeEnum.pass, DrugCategoryEnum.H);
		Long zPassNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndVoteTypeEnumAndDrugFormCommonNameContentsCommonDrugCategory(userId, reviewProcessId, VoteTypeEnum.pass, DrugCategoryEnum.Z);
		
		Long opposeNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndVoteTypeEnum(userId, reviewProcessId, VoteTypeEnum.oppose);
		Long hOpposeNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndVoteTypeEnumAndDrugFormCommonNameContentsCommonDrugCategory(userId, reviewProcessId, VoteTypeEnum.oppose, DrugCategoryEnum.H);
		Long zOpposeNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndVoteTypeEnumAndDrugFormCommonNameContentsCommonDrugCategory(userId, reviewProcessId, VoteTypeEnum.oppose, DrugCategoryEnum.Z);

		Long abstainNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndVoteTypeEnum(userId, reviewProcessId, VoteTypeEnum.abstain);
		Long hAbstainNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndVoteTypeEnumAndDrugFormCommonNameContentsCommonDrugCategory(userId, reviewProcessId, VoteTypeEnum.abstain, DrugCategoryEnum.H);
		Long zAbstainNumber = getVoteRecordRepository().countByUserIdAndReviewProcessIdAndVoteTypeEnumAndDrugFormCommonNameContentsCommonDrugCategory(userId, reviewProcessId, VoteTypeEnum.abstain, DrugCategoryEnum.Z);
		
		return String.format("药品总数：%d个（西药：%d，中成药：%d）；通过数：%d（西药：%d，中成药：%d）；反对数：%d（西药：%d，中成药：%d）；弃权数：%d（西药：%d，中成药：%d）。", recordNumber, hRecordNumber, zRecordNumber, passNumber, hPassNumber, zPassNumber, opposeNumber, hOpposeNumber, zOpposeNumber, abstainNumber, hAbstainNumber, zAbstainNumber);
	}

}
