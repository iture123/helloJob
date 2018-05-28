package com.helloJob.service.job;

import com.helloJob.model.job.HostInfo;


public interface HostInfoService{
	public boolean add(HostInfo hostInfo);
	
	public boolean update(HostInfo hostInfo);
	
	public boolean delete(Integer id);
}
