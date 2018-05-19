package com.helloJob.service.job;

import java.util.List;
import java.util.Set;

public interface JobInstanceService {
	
	/**
	 * 判断该作业是否首次执行
	 * **/
	boolean firstExecute(Long jobId); 
	
	/**
	 * 获取上级作业自依赖失败的作业列表
	 * */
	public List<Long> getRelyJobFailInstance (Long jobId,Integer dt);
	
	public void add(Long jobId,Integer dt);
	
	/**
	 * 判断该作业是否满足自依赖的条件
	 * ***/
	boolean isSelfRely(Long jobId,Integer dt);

	void delete(Long jobId, Integer dt);
	
	void delete(Set<Long> jobIds, Integer dt);
}
