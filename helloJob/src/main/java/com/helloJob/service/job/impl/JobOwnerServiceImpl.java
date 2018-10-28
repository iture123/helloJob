package com.helloJob.service.job.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.helloJob.mapper.admin.UserMapper;
import com.helloJob.mapper.job.JobOwnerMapper;
import com.helloJob.model.admin.User;
import com.helloJob.model.job.JobOwner;
import com.helloJob.service.job.JobOwnerService;

@Service
public class JobOwnerServiceImpl implements JobOwnerService{
	@Autowired
	private JobOwnerMapper jobOwnerMapper;
	@Autowired
	private UserMapper userMapper;
	@Override
	public List<Long> getOwnerIdByJobId(Long jobId) {
		Wrapper<JobOwner> wrapper = new EntityWrapper<>();
		wrapper.where("job_id={0}", jobId);
		List<JobOwner> owners = jobOwnerMapper.selectList(wrapper);
		return owners.stream().map(x->x.getUserId()).collect(Collectors.toList());
	}
	
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
		this.deleteOwner(jobId);
		this.add(jobId, userIds);
	}

	@Override
	public List<User> getOwnerByJobId(Long jobId) {
		List<Long> userIds = this.getOwnerIdByJobId(jobId);
		List<User> users = null;
		if(userIds.size()>0) {
			users = userMapper.selectBatchIds(userIds);
		}else {
			users = new ArrayList<>();
		}
		return users;
	}

	@Override
	public List<Long> getOwnerIds(List<User> owners) {
		if(owners == null || owners.size() ==0) {
			return  new ArrayList<>();
		}else {
			return owners.stream().map(x-> x.getId()).collect(Collectors.toList());
		}
	}

	@Override
	public void deleteOwner(Long jobId) {
		Wrapper<JobOwner> wrapper = new EntityWrapper<>();
		wrapper.where("job_id={0}", jobId);
		jobOwnerMapper.delete(wrapper );
	}

	@Override
	public List<String> getOwnerNames(List<User> owners) {
		if(owners == null || owners.size() ==0) {
			return  new ArrayList<>();
		}else {
			return owners.stream().map(x-> x.getName()).collect(Collectors.toList());
		}
	}

	

}
