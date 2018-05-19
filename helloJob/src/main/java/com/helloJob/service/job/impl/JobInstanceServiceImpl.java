package com.helloJob.service.job.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.helloJob.mapper.job.JobInstanceMapper;
import com.helloJob.model.job.JobInstance;
import com.helloJob.service.job.JobInstanceService;
import com.helloJob.utils.DateUtils;
@Service
public class JobInstanceServiceImpl  extends ServiceImpl<JobInstanceMapper, JobInstance> implements JobInstanceService {
	@Autowired
	private JobInstanceMapper jobInstanceMapper;
	@Override
	public boolean firstExecute(Long jobId) {
		Wrapper<JobInstance> wrapper = new EntityWrapper<JobInstance>();
		wrapper.where("job_id= {0} ", jobId);
		Integer count = jobInstanceMapper.selectCount(wrapper);
		return count > 0 ;
	}

	@Override
	public List<Long> getRelyJobFailInstance(Long jobId, Integer dt) {
		return jobInstanceMapper.getRelyJobFailInstance(jobId,dt);
	}

	@Override
	public void add(Long jobId, Integer dt) {
		JobInstance bean = new JobInstance();
		bean.setCreateTime(DateUtils.getCreateTime());
		bean.setJobId(jobId);
		bean.setDt(dt);
		bean.setId(jobId,dt);
		jobInstanceMapper.insert(bean);
	}



	@Override
	public void delete(Long jobId, Integer dt) {
		jobInstanceMapper.deleteById(jobId+"_"+dt);
	}

	public boolean beforeDtHasSuccess(Long jobId,Integer dt) {
		Wrapper<JobInstance> wrapper = new EntityWrapper<>();
		wrapper.where("job_id={0} and dt >= {1}", jobId,dt);
		int count = jobInstanceMapper.selectCount(wrapper );
		return count == 0;
	}
	public boolean preDtIsSuccess(Long jobId, Integer dt) {
		Date berforDtDate = DateUtils.addDay(DateUtils.parse(dt+"", "yyyyMMdd"), -1);
		String beforDt =DateUtils.getFormatDate(berforDtDate, "yyyyMMdd");
		Wrapper<JobInstance> wrapper = new EntityWrapper<>();
		wrapper.where("id={0}", jobId+"_"+beforDt);
		int count = jobInstanceMapper.selectCount(wrapper );
		return count == 1;
	}
	@Override
	public boolean isSelfRely(Long jobId, Integer dt) {
		if(preDtIsSuccess(jobId, dt)) {
			return true;
		}
		if(! beforeDtHasSuccess(jobId,dt)) {
			return true;
		}
		return false;
	}

	@Override
	public void delete(Set<Long> jobIds, Integer dt) {
		if(CollectionUtils.isNotEmpty(jobIds)) {
			jobInstanceMapper.delete(jobIds,dt);
		}
	}

}
