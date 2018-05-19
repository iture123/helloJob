package com.helloJob.service.admin.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.helloJob.mapper.admin.UserRoleMapper;
import com.helloJob.model.admin.UserRole;
import com.helloJob.service.admin.IUserRoleService;

/**
 *
 * UserRole 表数据服务层接口实现类
 *
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}