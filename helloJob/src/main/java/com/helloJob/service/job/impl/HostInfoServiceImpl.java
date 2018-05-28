package com.helloJob.service.job.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helloJob.mapper.job.HostInfoMapper;
import com.helloJob.model.job.HostInfo;
import com.helloJob.service.job.HostInfoService;
@Service
public class HostInfoServiceImpl  implements HostInfoService {
	@Autowired
	private HostInfoMapper hostInfoMapper;

	@Override
	public boolean add(HostInfo hostInfo) {
		return hostInfoMapper.insert(hostInfo)==1;
	}

	@Override
	public boolean update(HostInfo hostInfo) {
		return hostInfoMapper.updateById(hostInfo)==1;
	}

	@Override
	public boolean delete(Integer id) {
		return hostInfoMapper.deleteById(id)==1;
	}
}	
