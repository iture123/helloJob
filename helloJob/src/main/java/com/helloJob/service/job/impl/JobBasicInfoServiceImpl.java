package com.helloJob.service.job.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.helloJob.commons.result.PageInfo;
import com.helloJob.mapper.job.JobBasicInfoMapper;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.service.job.JobBasicInfoService;
import com.helloJob.vto.ComboboxVto;

@Service
public class JobBasicInfoServiceImpl extends ServiceImpl<JobBasicInfoMapper, JobBasicInfo>
		implements JobBasicInfoService {
	@Autowired
	private JobBasicInfoMapper jobBasicInfoMapper;

	@Override
	public void save(JobBasicInfo job) {
		jobBasicInfoMapper.insert(job);
	}

	@Override
	public List<JobBasicInfo> getPreJobList(Long jobId, String jobInfo) {
		return jobBasicInfoMapper.getPreJobList(jobId,jobInfo);
	}

	@Override
	public JobBasicInfo get(Long id) {
		return jobBasicInfoMapper.selectById(id);
	}

	@Override
	public void delJob(Long jobId) {
		jobBasicInfoMapper.deleteById(jobId);
	}


	@Override
	public void update(JobBasicInfo job) {
		jobBasicInfoMapper.updateById(job);
	}

	@Override
	public void getJobInfoList(PageInfo pageInfo) {
		Page<JobBasicInfo> page = new Page<JobBasicInfo>(pageInfo.getNowpage(), pageInfo.getSize());
		page.setOrderByField(pageInfo.getSort());
	    page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
		List<Map<String, Object>> list = jobBasicInfoMapper.getJobInfoList(page,pageInfo.getCondition());
		pageInfo.setRows(list);
		pageInfo.setTotal(page.getTotal());
	}

	@Override
	public List<ComboboxVto> getHasJobUserList() {
		return jobBasicInfoMapper.getHasJobUserList();
	}
	
}
