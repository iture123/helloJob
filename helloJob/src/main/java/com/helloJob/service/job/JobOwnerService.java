package com.helloJob.service.job;

import java.util.List;

import com.helloJob.model.admin.User;

/**
 * 任务责任人
 *
 */
public interface JobOwnerService {
	/**
	 * 
	 */
	public List<User> getOwnerByJobId(Long jobId);
	
	public List<String> getOwnerNames(List<User> owners);
	
	public List<Long> getOwnerIds(List<User> owners);
	/**
	 * 通过作业id获取责任人id列表
	 * @param jobId
	 * @return
	 */
	public List<Long> getOwnerIdByJobId(Long jobId);
	/**
	 * 通过作业id获取责任人邮箱地址
	 * @param jobId
	 * @return
	 */
	public List<String> getOwnerEmailByJobId(Long jobId);
	
	/**
	 * 添加责任人
	 * @param jobId
	 * @param userIds
	 */
	 public void add(Long jobId,List<Long> userIds);
	 
	 /**
		 * 修改责任人
		 * @param jobId
		 * @param userIds
		 */
	public void update(Long jobId,List<Long> userIds);
	/**
	 * 清空改作业的责任人
	 * @param jobId
	 */
	public void deleteOwner(Long jobId);
	
}
