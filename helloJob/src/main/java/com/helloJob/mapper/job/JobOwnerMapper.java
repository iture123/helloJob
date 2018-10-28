package com.helloJob.mapper.job;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.helloJob.model.job.JobOwner;

public interface JobOwnerMapper  extends BaseMapper<JobOwner>{
	public List<String> getOwnerEmailByJobId(@Param("jobId") Long jobId);
	
}
