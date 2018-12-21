package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;

public interface CommonNameContentsRepository extends BaseRepository<CommonNameContents, Long> {
	@Query("from CommonNameContents c where (c.common.spell like %?1% or c.common.spellSimplify like %?1%) and c.deleted is false and c.declared is true")
	List<CommonNameContents> findCommonNameContentsBySpell(String spell);
	
	@Query("from CommonNameContents c where c.manufacturer like ?1% ")
	List<CommonNameContents> findCommonNameContentsByManufacturer(String manufacturer);	
	
	@Query("select distinct projectName from CommonNameContents")
	List<String> findDistinctProjectName();
//	List<CommonNameContents> findByCommonIdAndDeletedFalse(Long commonId);
    
	//查询归为一品的大目录集合
	Page<CommonNameContents> findByCommonIdInAndAdministrationIdAndDeletedFalseAndDeclaredTrue(List<Long> commonIds, Long administrationId, Pageable pageable);
	
//	List<CommonNameContents> findByCommonCommonNameAndCommonAdministrationIdAndDeletedFalseAndDeclaredTrueOrderByUpdateDateDesc(String commonName, Long administrationId);
	
	@Modifying
	@Query("update CommonNameContents  set deleted = true where deleted is false")
	void deleteAllCommonNameContents();
	
	@Modifying
	@Query("update CommonNameContents  set declared = false where deleted is false and declared is true")
	void setAllValidRecordDeclaredFalse();	
	
	@Modifying
	@Query("update CommonNameContents  set declared = true where deleted is false and declared is false and projectName in (?1)")
	void setDeclaredTrueByProjectName(List<String> projectDeclareds);
	//增量导入，需要按照提取通用名，给药途径，编号，药品类型，大目录通用名，生产企业，剂型,规格，包装数量,省招标通用名,省招标药品ID，国家ID，项目名称查重，重复记录的不保存
	List<CommonNameContents> findByCommonCommonNameAndAdministrationIdAndCommonDrugCategoryAndCommonMatchNumberAndPillAndManufacturerAndCommonNameAndSpecificationsAndAmountAndCommonBidCommonNameAndBidDrugIdAndCountryIdAndProjectNameAndDeletedFalse(String extractCommonName, Long administrationId,DrugCategoryEnum drugCategory,String number,String pill,String manufacturer,String commonName,String specifications,String amount,String bidCommonName, String bidDrugId, String countryId, String projectName);
}
