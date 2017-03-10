package com.ewcms.empi.card.manage.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.Reflections;
import com.ewcms.empi.card.manage.entity.MatchRule;
import com.ewcms.empi.card.manage.entity.PatientBaseInfo;
import com.google.common.collect.Lists;

/**
 *@author zhoudongchu
 */
public class PatientBaseInfoRepositoryImpl {
    @PersistenceContext
    private EntityManager em;
    
    @SuppressWarnings("unchecked")
    public List<PatientBaseInfo> findRepeatByMatchRule(List<MatchRule> matchRuleList){
    	//select * from card_patient_base_info a,( select name,telephone from card_patient_base_info group by name,telephone having count(1) > 1 ) b where a.name = b.name and a.telephone = b.telephone
    	if(EmptyUtil.isCollectionEmpty(matchRuleList))return Lists.newArrayList();
    	StringBuilder hql = new StringBuilder("select ");
    	String columns = "";
    	for(MatchRule matchRule:matchRuleList){
    		columns += matchRule.getFieldName() + ", ";
    	}
    	columns = columns.substring(0, columns.length() - 2);
    	hql.append(columns);
    	hql.append(" from PatientBaseInfo  group by ");
    	hql.append(columns);
    	hql.append(" having count(1) > 1 ");

    	Query q = em.createQuery(hql.toString());
    	List<Object[]> results = q.getResultList();
    	int i;
    	List<PatientBaseInfo> patientBaseInfoList = Lists.newArrayList();
    	 for (Object[] result : results) {
    		 PatientBaseInfo m = new PatientBaseInfo();
    		 i = 0;
    		 for(MatchRule matchRule:matchRuleList){
    	    		Reflections.setFieldValue(m, matchRule.getFieldName(), result[i++]);
    		 }
    		 patientBaseInfoList.add(m);
    	  }
    	return patientBaseInfoList;
    }
    
    
    @SuppressWarnings("unchecked")
    public List<PatientBaseInfo> findNoRepeatByMatchRule(List<MatchRule> matchRuleList){
    	//select * from card_patient_base_info a,( select name,telephone from card_patient_base_info group by name,telephone having count(1) > 1 ) b where a.name = b.name and a.telephone = b.telephone
    	//update card_practice_card_index  set patient_id = RIGHT(REPLICATE('0',LEN(patient_id))+CAST(patient_base_info_id AS varchar(10)),LEN(patient_id))  where patient_base_info_id<>CAST(patient_id AS int) and patient_base_info_id in(select id from card_patient_base_info a,(select name,telephone from card_patient_base_info group by name,telephone having count(1) = 1) b where a.name=b.name and a.telephone=b.telephone)
    	if(EmptyUtil.isCollectionEmpty(matchRuleList))return Lists.newArrayList();
    	StringBuilder hql = new StringBuilder("select ");
    	String columns = "";
    	for(MatchRule matchRule:matchRuleList){
    		columns += matchRule.getFieldName() + ", ";
    	}
    	columns = columns.substring(0, columns.length() - 2);
    	hql.append(columns);
    	hql.append(" from PatientBaseInfo  group by ");
    	hql.append(columns);
    	hql.append(" having count(1) = 1 ");

    	Query q = em.createQuery(hql.toString());
    	List<Object[]> results = q.getResultList();
    	int i;
    	List<PatientBaseInfo> patientBaseInfoList = Lists.newArrayList();
    	 for (Object[] result : results) {
    		 PatientBaseInfo m = new PatientBaseInfo();
    		 i = 0;
    		 for(MatchRule matchRule:matchRuleList){
    	    		Reflections.setFieldValue(m, matchRule.getFieldName(), result[i++]);
    		 }
    		 patientBaseInfoList.add(m);
    	  }
    	return patientBaseInfoList;
    }
    

}
