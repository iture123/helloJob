package com.helloJob.service.job;

import java.util.List;

/**
 * 任务责任人
 *
 */
public interface JobOwnerService {
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
}
