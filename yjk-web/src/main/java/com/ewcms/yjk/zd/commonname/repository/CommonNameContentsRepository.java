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
	
//	List<CommonNameContents> findByCommonIdAndDeletedFalse(Long commonId);
//	
	Page<CommonNameContents> findByCommonIdAndDeletedFalseAndDeclaredTrue(Long commonId, Pageable pageable);
	
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
	
	List<CommonNameContents> findByCommonCommonNameAndAdministrationIdAndCommonDrugCategoryAndCommonMatchNumberAndPillAndManufacturerAndCommonNameAndSpecificationsAndAmountAndCommonBidCommonNameAndDeletedFalse(String extractCommonName, Long administrationId,DrugCategoryEnum drugCategory,String number,String pill,String manufacturer,String commonName,String specifications,String amount,String bidCommonName);
}
