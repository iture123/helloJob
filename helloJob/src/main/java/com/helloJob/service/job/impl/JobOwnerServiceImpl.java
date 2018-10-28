package com.helloJob.service.job.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.helloJob.mapper.job.JobOwnerMapper;
import com.helloJob.model.job.JobOwner;
import com.helloJob.service.job.JobOwnerService;

@Service
public class JobOwnerServiceImpl implements JobOwnerService{
	@Autowired
	private JobOwnerMapper jobOwnerMapper;
	
	@Override
	public List<String> getOwnerEmailByJobId(Long jobId) {
		return jobOwnerMapper.getOwnerEmailByJobId(jobId);
	}

	@Override
	public void add(Long jobId, List<Long> userIds) {
		for(Long userId:userIds) {
			JobOwner owner = new JobOwner();
			owner.setJobId(jobId);
			owner.setUserId(userId);
			jobOwnerMapper.insert(owner);
		}
		
	}

	@Override
	public void update(Long jobId, List<Long> userIds) {
		Wrapper<JobOwner> wrapper = new EntityWrapper<>();
		wrapper.where("job_id=?", jobId);
		jobOwnerMapper.delete(wrapper );
		this.add(jobId, userIds);
	}

}
