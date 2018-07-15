package com.helloJob.service.job.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.helloJob.commons.result.PageInfo;
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

	@Override
	public void datagrid(PageInfo pageInfo) {
		Page<HostInfo> page = new Page<>(pageInfo.getNowpage(), pageInfo.getSize());
		page.setOrderByField(pageInfo.getSort());
	    page.setAsc(pageInfo.getOrder().equalsIgnoreCase("asc"));
		Wrapper<HostInfo> wrapper = new EntityWrapper<>();
		List<HostInfo> list = hostInfoMapper.selectList(wrapper );
		pageInfo.setRows(list);
		pageInfo.setTotal(page.getTotal());		
	}

	@Override
	public HostInfo get(Integer id) {
		return hostInfoMapper.selectById(id);
	}

	@Override
	public List<HostInfo> getAllList() {
		Wrapper<HostInfo> entity = new EntityWrapper<>();
		List<HostInfo> list = hostInfoMapper.selectList(entity );
		list.forEach(x-> x.setPasswd(null));
		return list;
	}
}	
