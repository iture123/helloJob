package com.helloJob.service.job;

import java.util.List;

import com.helloJob.commons.result.PageInfo;
import com.helloJob.model.job.HostInfo;


public interface HostInfoService{
	public boolean add(HostInfo hostInfo);
	
	public boolean update(HostInfo hostInfo);
	
	public boolean delete(Integer id);

	public void datagrid(PageInfo pageInfo);

	public HostInfo get(Integer id);

	public List<HostInfo> getAllList();
}
