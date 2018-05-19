package com.helloJob.service.job;

import java.util.List;
import java.util.Set;

import com.helloJob.commons.result.Tree;
import com.helloJob.model.job.ScheRelyJob;

public interface ScheRelyJobService {
	public void save(Long jobId,Long[] relyPreJob);

	public void deleteParentJobs(Long jobId);
	
	/**
	 * 获取被该作业触发的子作业列表
	 * */
	List<ScheRelyJob> getTriggerJobList(Long jobId);
	
	/**
	 * 获取依赖的父作业和触发的子作业列表
	 * **/
	public List<Long> getReferJobIdsOfOneJob(Long jobId);
	/**
	 * 通过一个节点的作业id，获取和该节点相关的作业树
	 * **/
	public Set<Long> getReferJobIds(Long jobId);
	
	/**
	 * 获取作业树
	 * ***/
	public List<Tree> getTreeList(Long jobId);
	/**
	 * 获取其要触发的子作业(不含孙子作业)
	 * **/
	public List<Long> getTriggerJobs(Long jobId);
	/**
	 * 获取其要触发的子孙作业
	 * **/
	public Set<Long> getAllTriggerJobs(Long jobId);
}
