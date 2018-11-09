package com.ewcms.yjk.zd.commonname.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.yjk.zd.commonname.entity.Administration;
import com.ewcms.yjk.zd.commonname.entity.CommonNameContents;
import com.ewcms.yjk.zd.commonname.entity.DrugCategoryEnum;

public interface CommonNameContentsRepository extends BaseRepository<CommonNameContents, Long> {
	@Query("select distinct c.common.administration from CommonNameContents c where c.common.commonName=?1 and c.deleted is false and c.declared is true")
	List<Administration> findAdministrationByCommonName(String commonName);
	
	List<CommonNameContents> findByCommonIdAndDeletedFalse(Long commonId);
	
	Page<CommonNameContents> findByCommonIdInAndDeletedFalseAndDeclaredTrue(List<Long> commonIds, Pageable pageable);
	
	List<CommonNameContents> findByCommonCommonNameAndCommonAdministrationIdAndDeletedFalseAndDeclaredTrueOrderByUpdateDateDesc(String commonName, Long administrationId);
	
	@Modifying
	@Query("update CommonNameContents  set deleted = true where deleted is false")
	void deleteAllCommonNameContents();
	
	List<CommonNameContents> findByCommonCommonNameAndCommonAdministrationIdAndCommonDrugCategoryAndCommonNumberAndPillAndManufacturerAndCommonNameAndDeletedFalse(String extractCommonName, Long administrationId,DrugCategoryEnum drugCategory,String number,String pill,String manufacturer,String commonName);
}
