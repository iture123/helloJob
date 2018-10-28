package com.helloJob.service.job;

import java.util.List;

import com.helloJob.commons.result.PageInfo;
import com.helloJob.model.job.JobType;

public interface JobTypeService {
	public String getName(Long id);
	
	public void add(JobType jobType);
	
	public void grid(PageInfo pageInfo);
	
	public  List<JobType>  combobox();

	public void update(JobType jobType);
}
