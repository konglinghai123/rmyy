package com.ewcms.system.scheduling.generate.job.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ewcms.common.repository.BaseRepository;
import com.ewcms.system.scheduling.generate.job.report.entity.JobReport;
import com.ewcms.system.scheduling.generate.job.report.entity.JobReportParameter;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface JobReportRepository extends BaseRepository<JobReport, Long> {
	
	@Query("select p from JobReport r left join r.jobReportParameters p where r.id=?1")
	List<JobReportParameter> findByJobReportParameterById(Long jobReportId);
	
	JobReport findJobReportByReportIdAndReportType(final Long reportId, final String reportType);
}
