package com.helloJob.service.job;

import java.util.List;
import java.util.Set;

import com.helloJob.model.job.ScheBasicInfo;

public interface ScheBasicInfoService {
	public void save(ScheBasicInfo scheInfo);

	public void mountScheByTime(Long jobId, String cron);

	public void runOnce(long jobId, int dt, String isSelfRely);
	
	public ScheBasicInfo getScheInfo(Long jobId);
	/**
	 * 停用根据时间来调度的作业
	 * **/
	public void stopTimeSchedule(Long jobId);

	/**
	 * 停用依赖上级来调度的作业
	 * **/
	public void stopRelyJobSchedule(Long jobId);
	/**
	 * 获取根据时间来调度信息
	 * **/
	public List<ScheBasicInfo> getScheByTime();
	/**
	 * 终止正在执行的作业
	 * ***/
	public void killJobs(Set<Long> jobIds, Integer dt,String firstLineLog);
}
