package com.helloJob.mapper.job;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.helloJob.model.job.JobLog;
import com.helloJob.vto.JobLogVto;

public interface JobLogMapper  extends BaseMapper<JobLog>{
	
	List<JobLogVto> grid(Pagination page, Map<String, Object> params);
	  

	void updateRunningToError();

	List<String> getRunningJobIds(@Param("jobIds") Set<Long> jobIds,@Param("dt") Integer dt);

	String getJobState(@Param("jobId") Long jobId,@Param("dt")  Integer dt);
	
	
	public void updateForFinish(@Param("jobLogId")  String jobLogId,@Param("jobState") String jobState,@Param("endTime") String endTime);
}
