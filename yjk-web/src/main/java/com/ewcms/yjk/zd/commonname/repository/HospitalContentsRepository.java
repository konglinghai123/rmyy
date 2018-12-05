package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;
import com.ewcms.yjk.zd.commonname.entity.HospitalContents;

/**
 *@author zhoudongchu
 */
public interface HospitalContentsRepository extends BaseRepository<HospitalContents, Long> {
	//查询一品的院用集合
	List<HospitalContents> findByCommonIdInInAndAdministrationIdAndDeletedFalse(List<Long> commonIds, Long administrationId);
	//分页查询一品的院用集合
	Page<HospitalContents> findByCommonIdInInAndAdministrationIdAndDeletedFalse(List<Long> commonIds, Long administrationId, Pageable pageable);
	
	//按照提取通用名，给药途径，编号，药品类型，院用目录通用名，生产企业，剂型,规格，包装数量,省招标通用名查询院用集合
	List<HospitalContents> findByCommonCommonNameAndAdministrationIdAndCommonDrugCategoryAndCommonMatchNumberAndPillAndManufacturerAndCommonNameAndSpecificationsAndAmountAndCommonBidCommonNameAndDeletedFalse(String extractCommonName, Long administrationId,DrugCategoryEnum drugCategory,String matchNumber,String pill,String manufacturer,String commonName,String specifications,String amount,String bidCommonName);
	
	//删除所有的院用记录
	@Modifying
	@Query("update HospitalContents  set deleted = true where deleted is false")
	void deleteAllHospitalContents();
}
