package com.helloJob.service.job.impl;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.helloJob.commons.result.PageInfo;
import com.helloJob.constant.JobStateConst;
import com.helloJob.mapper.job.JobLogMapper;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.model.job.JobLog;
import com.helloJob.service.job.JobLogService;
import com.helloJob.utils.DateUtils;
import com.helloJob.vto.JobLogVto;

import cn.hutool.core.date.DateUtil;
@Service
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper,  JobLog> implements JobLogService {
	@Autowired
	private JobLogMapper jobLogMapper;
	@Override
	public JobLog get(String id) {
		return jobLogMapper.selectById(id);
	}

	@Override
	public void update(JobLog jobLog) {
		jobLogMapper.updateById(jobLog);
	}
	@Override
	public String seeLog(String jobLogId) {
		return  jobLogMapper.selectById(jobLogId).getLog();
	}

	@Override
	public void add(JobLog jobLog) {
		jobLogMapper.insert(jobLog);
	}

	@Override
	public JobLog addRunningLog(long jobId, int dt,JobBasicInfo job) {
		JobLog jobLog = new JobLog();
		jobLog.setJobId(jobId);
		jobLog.setDt(dt);
		jobLog.setBeginTime(DateUtils.getCreateTime());
		jobLog.setJobState(JobStateConst.RUNNING);
		jobLog.setId(UUID.randomUUID().toString()); 
		jobLog.setJobImg(JSON.toJSONString(job));
		this.add(jobLog);
		return jobLog;
	}

	/*
	 * @Override public void updateError(JobLog jobLog, String error) {
	 * jobLog.setJobState(JobStateConst.ERROR); jobLog.setLog(error);
	 * jobLog.setEndTime(DateUtils.getCreateTime());
	 * jobLogMapper.updateById(jobLog); }
	 */

	@Override
	public void add(Long jobId, Integer dt, String state, String log) {
		JobLog jobLog = new JobLog();
		jobLog.setJobId(jobId);
		jobLog.setDt(dt);
		jobLog.setBeginTime(DateUtils.getCreateTime());
		jobLog.setJobState(state);
		jobLog.setId(UUID.randomUUID().toString()); 
		jobLog.setLog(log);
		this.add(jobLog);
	}

	@Override
	public void grid(PageInfo pageInfo) {
		Page<JobLog> page = new Page<JobLog>(pageInfo.getNowpage(), pageInfo.getSize());
		page.setOrderByField(pageInfo.getSort());
	    page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
	    List<JobLogVto> rows = jobLogMapper.grid(page, pageInfo.getCondition());
	    rows.forEach(x ->{
	    	if(StringUtils.isNotEmpty(x.getEndTime())) {
	    		Long ms = DateUtil.parse(x.getEndTime()).getTime()- DateUtil.parse(x.getBeginTime()).getTime() ;
	    		int elapsedTime = ms.intValue()/1000;
	    		if(elapsedTime > 60) {
	    			int minute =elapsedTime / 60 ;
	    			int ss = elapsedTime % 60;
	    			x.setElapsedTime(String.format("%d分%d秒", minute,ss));
	    		}else {
	    			x.setElapsedTime(elapsedTime+"秒");
	    		}
	    	}
	    });
		pageInfo.setRows(rows);
		pageInfo.setTotal(page.getTotal());
	}

	@Override
	public String getJobState(String jobLogId) {
		return get(jobLogId).getJobState();
	}

	@Override
	public void updateRunningToError() {
		jobLogMapper.updateRunningToError();
		
	}

	@Override
	public List<String> getRunningJobLogIds(Set<Long> jobIds, Integer dt) {
		if(CollectionUtils.isEmpty(jobIds)) {
			return Lists.newArrayList();
		}
		return jobLogMapper.getRunningJobIds(jobIds,dt);
	}

	@Override
	public String getJobState(Long jobId, Integer dt) {
		return jobLogMapper.getJobState(jobId,dt);
	}

	@Override
	public void updateLog(String jobLogId, String log) {
		JobLog jobLog = this.get(jobLogId);
		jobLog.setLog(log);
		jobLogMapper.updateById(jobLog);
	}


	@Override
	public void updateForFinish(String jobLogId, String jobState) {
		String endTime = DateUtils.getCreateTime();
		jobLogMapper.updateForFinish(jobLogId, jobState, endTime );
	}

	@Override
	public void updateSuccess(String jobLogId) {
		this.updateForFinish(jobLogId, JobStateConst.SUCCESS);
	}

	@Override
	public void updateError(String jobLogId) {
		this.updateForFinish(jobLogId, JobStateConst.ERROR);
	}



}
