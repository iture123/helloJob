package com.helloJob.service.admin.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.helloJob.mapper.admin.RoleResourceMapper;
import com.helloJob.model.admin.RoleResource;
import com.helloJob.service.admin.IRoleResourceService;

/**
 *
 * RoleResource 表数据服务层接口实现类
 *
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {


}