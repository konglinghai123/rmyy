package com.ewcms.empi.card.manage.repository;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.ewcms.empi.card.manage.entity.PracticeCard;

/**
 *
 * @author wu_zhijun
 */
public interface PracticeCardRepository extends BaseRepository<PracticeCard, Long>{

	@Query("select c.patientBaseInfo.id from PracticeCard c where c.practiceNo=?1 and c.deleted=?2")
	Long findPatientIdByPracticeNo(String practiceNo, Boolean deleted);
	
	@Query("select c.patientBaseInfo from PracticeCard c where c.practiceNo=?1 and c.deleted=?2")
	PatientBaseInfo findPatientBaseInfoByPracticeNo(String practiceNo, Boolean deleted);
}
