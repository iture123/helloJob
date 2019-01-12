package com.helloJob.service.job;

import java.util.List;
import java.util.Set;

import com.helloJob.commons.result.PageInfo;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.model.job.JobLog;

public interface JobLogService {
	public void add(JobLog jobLog);
	
	public JobLog addRunningLog(long jobId,int dt,JobBasicInfo job);
	
	public JobLog get(String id);

	public void update(JobLog jobLog);
	
	public String seeLog(String jobLogId);

	/**
	 * 更新作业执行的最终状态
	 * @param jobLogId
	 * @param finishState
	 */
	public void updateForFinish(String jobLogId,String jobState);
	/**
	 * 更新作业状态为成功
	 * @param jobLogId
	 */
	public void updateSuccess(String jobLogId);
	/**
	 * 更新作业状态为失败
	 * @param jobLogId
	 */
	public void updateError(String jobLogId);
	
	public void add(Long jobId, Integer dt, String state, String log);

	public void grid(PageInfo pageInfo);
	
	public String getJobState(String jobLogId);
	/***
	 * 将状态为运行中的记录更新为失败。
	 * ***/
	public void updateRunningToError();
	/***
	 * 获取日志状态为"执行中"的日志id列表
	 * **/
	List<String> getRunningJobLogIds(Set<Long> jobIds, Integer dt);
	/**
	 * 获取最新一条日志的状态
	 * **/
	String getJobState(Long jobId,Integer dt);
	/**
	 * 更新作业日志
	 * @param jobLogId
	 * @param log
	 */
	public void updateLog(String jobLogId,String log);
	
	
}
