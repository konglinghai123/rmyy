package com.ewcms.yjk.re.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.Collections3;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.yjk.re.entity.ReviewMain;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.entity.VoteRecord;
import com.ewcms.yjk.re.entity.VoteResult;
import com.ewcms.yjk.re.entity.VoteTypeEnum;
import com.ewcms.yjk.re.repository.VoteRecordRepository;
import com.ewcms.yjk.sb.entity.AuditStatusEnum;
import com.ewcms.yjk.sb.entity.DrugForm;
import com.ewcms.yjk.sb.service.DrugFormService;
import com.ewcms.yjk.zd.commonname.entity.CommonName;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.service.CommonNameContentsService;
import com.ewcms.yjk.zd.commonname.service.CommonNameService;
import com.ewcms.yjk.zd.commonname.service.HospitalContentsService;
import com.google.common.collect.Maps;

@Service
public class VoteRecordService extends BaseService<VoteRecord, Long> {
	@Autowired
	private DrugFormService drugFormService;
	@Autowired
	private ReviewMainService reviewMainService;
	@Autowired
	private VoteResultService voteResultService;
	@Autowired
	private CommonNameContentsService commonNameContentsService;
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

	// 专家投票评审监控
	public Map<DrugForm, Map<String, String>> findUserVote(Long voteUserId) {
		// 查询评审是否开启，并检验是否关联申报系统
		ReviewMain reviewMain = reviewMainService.findByEnabledTrue();
		if (reviewMain == null || reviewMain.getSystemParameter() == null)
			return Maps.newHashMap();

		// 评审流程是否存在
		List<ReviewProcess> reviewProcesses = reviewMain.getReviewProcesses();
		if (EmptyUtil.isCollectionEmpty(reviewProcesses))
			return Maps.newHashMap();

		// 获取申报系统编号，并查询本次初审所有通过的药品记录集
		List<DrugForm> drugForms = drugFormService
				.findByAuditStatusAndSystemParameterIdOrderByIdAsc(
						AuditStatusEnum.passed, reviewMain.getSystemParameter()
								.getId());

		Map<DrugForm, Map<String, String>> maps = Maps.newHashMap();
		for (DrugForm drugForm : drugForms) {
			Map<String, String> processMap = Maps.newHashMap();
			for (ReviewProcess reviewProcess : reviewProcesses) {
				VoteRecord voteRecord = findByUserIdAndReviewMainIdAndDrugFormIdAndReviewProcessIdAndSubmittedTrue(
						voteUserId, reviewMain.getId(), drugForm.getId(),
						reviewProcess.getId());
				String voteType = "";
				if (voteRecord != null)
					voteType = voteRecord.getVoteTypeEnum().getInfo();
				processMap.put(reviewProcess.getReviewBaseRule()
						.getRuleCnName(), voteType);
			}
			maps.put(drugForm, processMap);
		}
		return maps;
	}

	/**
	 * 启动专家新增通用名投票
	 * 
	 */
	public String expertStartVoteAddCommonName(Long userId,
			Long currentReviewProcessId) {
		return expertDrugFormInitVote(userId, currentReviewProcessId, "新增通用名");
	}

	/**
	 * 启动专家新增剂型规格的投票
	 * 
	 */
	public String expertStartVoteAddSpecificationsAndPill(Long userId,
			Long currentReviewProcessId) {
		return expertDrugFormInitVote(userId, currentReviewProcessId, "新增规格/剂型");
	}

	private String expertDrugFormInitVote(Long userId,Long currentReviewProcessId, String declareCategory) {
		ReviewMain reviewMainEnable = reviewMainService.findByEnabledTrue();
		if (reviewMainEnable == null)
			return "评审还未启动！";
		Boolean isExitsResult = voteResultService
				.countByReviewProcessId(currentReviewProcessId) == 0 ? Boolean.FALSE
				: Boolean.TRUE;// 判断是否已经有投票结果记录，没有就初始初始投票结果都为0的记录
		if (!isExitsResult) {
			List<DrugForm> validDrugFormList = drugFormService
					.findByAuditStatusAndSystemParameterIdAndDeclareCategoryAndReviewedFalseOrderByIdAsc(
							AuditStatusEnum.passed, reviewMainEnable.getSystemParameter().getId(),declareCategory);
			for (DrugForm drugForm : validDrugFormList) {
				VoteResult voteResult = new VoteResult();
				voteResult.setReviewMainId(reviewMainEnable.getId());
				voteResult.setReviewProcessId(currentReviewProcessId);
				voteResult.setDrugForm(drugForm);
				voteResult.setOpposeSum(0);
				voteResult.setPassSum(0);
				voteResult.setAbstainSum(0);
				voteResultService.save(voteResult);

			}
		}

		if (getVoteRecordRepository().countByUserIdAndReviewProcessId(userId,
				currentReviewProcessId).intValue() == 0) {// 初次进入投票，初始化需要投票的申报新曾通用名的药品
			List<VoteResult> voteResultList = voteResultService
					.findByReviewProcessId(currentReviewProcessId);
			for (VoteResult voteResult : voteResultList) {
				VoteRecord vo = new VoteRecord();
				vo.setSubmitted(Boolean.FALSE);
				vo.setUserId(userId);
				vo.setReviewMainId(reviewMainEnable.getId());
				vo.setReviewProcessId(voteResult.getReviewProcessId());
				vo.setDrugForm(voteResult.getDrugForm());
				vo.setCommonNameContents(voteResult.getCommonNameContents());
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
		return experteaddCommonNameManufacturerInitVote(userId, currentReviewProcessId, "新增通用名");
	}

	/**
	 * 启动专家新增规格/剂型厂家遴选投票
	 * 
	 */
	public String expertStartVoteAddSpecificationsAndPillManufacturer(Long userId,
			Long currentReviewProcessId) {
		return experteaddCommonNameManufacturerInitVote(userId, currentReviewProcessId, "新增规格/剂型");
	}
	

	@SuppressWarnings("unchecked")
	private String experteaddCommonNameManufacturerInitVote(Long userId,Long currentReviewProcessId,String declareCategory) {
		ReviewMain reviewMainEnable = reviewMainService.findByEnabledTrue();
		if (reviewMainEnable == null)
			return "评审还未启动！";
		List<DrugForm> selectedDrugFormList = voteResultService
				.findSelectedDrugForm(reviewMainEnable.getId(), declareCategory);
		Boolean isExitsResult = voteResultService
				.countByReviewProcessId(currentReviewProcessId) == 0 ? Boolean.FALSE
				: Boolean.TRUE;// 判断是否已经有投票结果记录，没有就初始初始投票结果都为0的记录
		if (!isExitsResult) {
			for (DrugForm drugForm : selectedDrugFormList) {
				CommonNameContents commonNameContentsvo = commonNameContentsService
						.findOne(drugForm.getCommonNameContents().getId());
				List<CommonName> commonNames = commonNameService
						.findByMatchNumber(commonNameContentsvo.getCommon()
								.getMatchNumber());
				List<Long> commonNameIds = Collections3.extractToList(
						commonNames, "id");
				List<CommonNameContents> machCommonNameContentsList = commonNameContentsService
						.findByCommonIdInAndAdministrationIdAndDeletedFalseAndDeclaredTrue(
								commonNameIds, commonNameContentsvo
										.getAdministration().getId());
				for (CommonNameContents commonNameContents : machCommonNameContentsList) {
					if(voteResultService.findByCommonNameContentsIdAndReviewProcessId(commonNameContents.getId(), currentReviewProcessId)==null){//已经存在的大总目录不再进入投票
						if (hospitalContentsService.findByBidDrugIdAndDeletedFalse(commonNameContents.getBidDrugId()) == null) {//院用目录已存在的不进行投票
							VoteResult voteResult = new VoteResult();
							voteResult.setReviewMainId(reviewMainEnable.getId());
							voteResult.setReviewProcessId(currentReviewProcessId);
							voteResult.setDrugForm(drugForm);
							voteResult.setOpposeSum(0);
							voteResult.setPassSum(0);
							voteResult.setAbstainSum(0);
							voteResult.setCommonNameContents(commonNameContents);
							voteResultService.save(voteResult);
						}
					}
				}
			}
		}

		if (getVoteRecordRepository().countByUserIdAndReviewProcessId(userId,
				currentReviewProcessId).intValue() == 0) {// 初次进入投票，初始化需要投票的申报新曾通用名的药品
			List<VoteResult> voteResultList = voteResultService
					.findByReviewProcessId(currentReviewProcessId);
			for (VoteResult voteResult : voteResultList) {
				VoteRecord vo = new VoteRecord();
				vo.setSubmitted(Boolean.FALSE);
				vo.setUserId(userId);
				vo.setReviewMainId(reviewMainEnable.getId());
				vo.setReviewProcessId(voteResult.getReviewProcessId());
				vo.setDrugForm(voteResult.getDrugForm());
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

					VoteResult voteResult = voteResultService
							.findByDrugFormIdAndReviewProcessId(vo
									.getDrugForm().getId(), vo
									.getReviewProcessId());
					if (voteResult != null
							&& !voteResult.getAffirmVoteResulted()) {
						if (voteTypeEnum.equals(VoteTypeEnum.pass)) {
							voteResult.setPassSum(voteResult.getPassSum() + 1);
						} else if (voteTypeEnum.equals(VoteTypeEnum.oppose)) {
							voteResult
									.setOpposeSum(voteResult.getOpposeSum() + 1);
						} else if (voteTypeEnum.equals(VoteTypeEnum.abstain)) {
							voteResult
									.setAbstainSum(voteResult.getAbstainSum() + 1);
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
	public List<Long> findUserSubmitted(Long reviewMainId, Long reviewProcessId) {
		return getVoteRecordRepository().findUserSubmitted(reviewMainId,
				reviewProcessId);
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
	 * @param reviewMainId
	 * @param reviewProcessId
	 */
	public void giveUp(Long userId, Long reviewMainId, Long reviewProcessId) {
		List<VoteRecord> voteRecords = findByUserIdAndReviewMainIdAndReviewProcessIdAndDrugFormIdIsNullAndCommonNameContentsIdIsNull(
				userId, reviewMainId, reviewProcessId);
		if (EmptyUtil.isCollectionEmpty(voteRecords)) {
			VoteRecord voteRecord = new VoteRecord();
			voteRecord.setUserId(userId);
			voteRecord.setReviewMainId(reviewMainId);
			voteRecord.setReviewProcessId(reviewProcessId);
			voteRecord.setVoteTypeEnum(VoteTypeEnum.abstain);
			voteRecord.setSigned(Boolean.TRUE);
			voteRecord.setSubmitted(Boolean.TRUE);
			voteRecord.setSubmittDate(new Date(Calendar.getInstance().getTime()
					.getTime()));
			super.save(voteRecord);
		}
	}

	/**
	 * 查询未签字的记录集
	 * 
	 * @param userId
	 *            用户编号
	 * @param reviewMainId
	 *            主评审编号
	 * @param reviewProcessId
	 *            流程编号
	 * @return
	 */
	public List<VoteRecord> findByUserIdAndReviewMainIdAndReviewProcessIdAndSignedFalse(
			Long userId, Long reviewMainId, Long reviewProcessId) {
		return getVoteRecordRepository()
				.findByUserIdAndReviewMainIdAndReviewProcessIdAndSignedFalse(
						userId, reviewMainId, reviewProcessId);
	}

	public void sign(Long userId, Long reviewMainId, Long reviewProcessId) {
		List<VoteRecord> voteRecords = findByUserIdAndReviewMainIdAndReviewProcessIdAndSignedFalse(
				userId, reviewMainId, reviewProcessId);
		if (EmptyUtil.isCollectionNotEmpty(voteRecords)) {
			getVoteRecordRepository().updateSigned(userId, reviewMainId,
					reviewProcessId);
		}
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
}
