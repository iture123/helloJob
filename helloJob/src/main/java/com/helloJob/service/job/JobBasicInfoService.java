package com.helloJob.service.job;

import java.util.List;

import org.springframework.stereotype.Service;

import com.helloJob.commons.result.PageInfo;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.vto.ComboboxVto;
@Service
public interface JobBasicInfoService {
	public void save(JobBasicInfo job);
	
	/**
	 * 获取可以依赖的上级作业
	 * */
	List<JobBasicInfo> getPreJobList(Long jobId,String jobInfo);
	
	public JobBasicInfo get(Long id);
	
	public void delJob(Long jobId);

	
	public void update(JobBasicInfo job);
	
	void getJobInfoList(  PageInfo pageInfo );
	/**
	 * 
	 * **/
	public List<ComboboxVto> getHasJobUserList();
}
