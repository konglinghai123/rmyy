package com.ewcms.system.scheduling.generate.job.report.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ewcms.system.scheduling.generate.job.report.entity.JobReport;

/**
 * @author 吴智俊
 */
public class JobReportRepositoryImpl {
	
	@PersistenceContext
	private EntityManager em;
	
	public JobReport findJobReportByReportIdAndReportType(final Long reportId, final String reportType) {
		String hql = "Select o From JobReport o Inner Join ";
		if (reportType.equals("text")){
			hql += " o.textReport c ";
		}else if (reportType.equals("chart")){
			hql += " o.chartReport c ";
		}
		hql += " Where c.id=:reportId ";

		TypedQuery<JobReport> query = em.createQuery(hql, JobReport.class);
    	query.setParameter("reportId", reportId);

    	JobReport ewcmsJobReport = null;
    	try{
    		ewcmsJobReport = (JobReport) query.getSingleResult();
    	}catch(Exception e){
    		
    	}
    	return ewcmsJobReport;
	}
}
