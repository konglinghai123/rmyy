package com.ewcms.yjk.re.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.common.plugin.service.BaseSequenceMovableService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.yjk.re.entity.EnsurePassThrough;
import com.ewcms.yjk.re.entity.ReviewProcess;
import com.ewcms.yjk.re.repository.EnsurePassThroughRepository;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;

@Service
public class EnsurePassThroughService extends BaseSequenceMovableService<EnsurePassThrough, Long>{
	
	private EnsurePassThroughRepository getEnsurePassThroughRepository() {
		return (EnsurePassThroughRepository) baseRepository;
	}
	
	@Autowired
	private ReviewProcessService reviewProcessService;

	public void changeStatus(List<Long> ensurePassThroughIds, Boolean newStatus) {
		getEnsurePassThroughRepository().changeStatus(ensurePassThroughIds, newStatus);
	}
	
	/**
	 * 查询特定科室定义的通过数
	 * 
	 * @param reviewProcessId
	 * @param organizationId
	 * @param drugCategoryEnum
	 * @return
	 */
	public int findEnsureNumber(Long reviewProcessId, Long organizationId, DrugCategoryEnum drugCategoryEnum) {
		int maxNumber = -1;// -1代表没有设定特定科室
		
		ReviewProcess reviewProcess = reviewProcessService.findOne(reviewProcessId);
		if (reviewProcess == null) return maxNumber;
		List<EnsurePassThrough> ensurePassThroughs = reviewProcess.getEnsurePassThrough();
		if (EmptyUtil.isCollectionEmpty(ensurePassThroughs)) return maxNumber;
		for (EnsurePassThrough ensurePassThrough : ensurePassThroughs) {
			Set<Long> organizationIds = ensurePassThrough.getOrganizationIds();
			
			if (EmptyUtil.isCollectionEmpty(organizationIds) || organizationIds.contains(organizationId)) {
				if (drugCategoryEnum.equals(DrugCategoryEnum.H)) {
					maxNumber = Math.max(maxNumber, ensurePassThrough.getWesternNumber().intValue());
				} else if (drugCategoryEnum.equals(DrugCategoryEnum.Z)) {
					maxNumber = Math.max(maxNumber, ensurePassThrough.getChineseNumber().intValue());
				}
			}
		}
		
		return maxNumber;
	}
}
