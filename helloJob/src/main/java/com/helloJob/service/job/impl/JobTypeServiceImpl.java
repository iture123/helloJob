package com.helloJob.service.job.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.helloJob.commons.result.PageInfo;
import com.helloJob.mapper.job.JobTypeMapper;
import com.helloJob.model.job.JobType;
import com.helloJob.service.job.JobTypeService;
@Service
public class JobTypeServiceImpl extends ServiceImpl<JobTypeMapper, JobType> implements JobTypeService {
	@Autowired
	private JobTypeMapper jobTypeMapper;
	@Override
	public void add(JobType jobType) {
		jobTypeMapper.insert(jobType);
	}
	@Override
	public void grid(PageInfo pageInfo) {
		Wrapper<JobType> wrapper = new EntityWrapper<JobType>();
		wrapper.orderBy("seq");
		List<JobType> dataList = jobTypeMapper.selectList(wrapper );
		pageInfo.setRows(dataList);
		pageInfo.setTotal(dataList.size());
	}
	@Override
	public List<JobType>  combobox() {
		Wrapper<JobType> wrapper = new EntityWrapper<JobType>();
		wrapper.orderBy("seq");
		List<JobType> dataList = jobTypeMapper.selectList(wrapper );
		return dataList;
	}
	@Override
	public void update(JobType jobType) {
		jobTypeMapper.updateById(jobType);
	}
	@Override
	public String getName(Long id) {
		return jobTypeMapper.selectById(id).getName();
	}

}
