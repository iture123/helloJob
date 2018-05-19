package com.helloJob.service.admin.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.helloJob.commons.result.Tree;
import com.helloJob.commons.shiro.ShiroUser;
import com.helloJob.mapper.admin.ResourceMapper;
import com.helloJob.mapper.admin.RoleMapper;
import com.helloJob.mapper.admin.RoleResourceMapper;
import com.helloJob.mapper.admin.UserRoleMapper;
import com.helloJob.model.admin.Resource;
import com.helloJob.service.admin.IResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * Resource 表数据服务层接口实现类
 *
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {
    private static final int RESOURCE_MENU = 0; // 菜单

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    public List<Resource> selectAll() {
        return selectAllByStatus(null);
    }

    private List<Resource> selectAllByStatus(Integer status) {
        Resource resource = new Resource();
        if (status != null) {
            resource.setStatus(status);
        }
        EntityWrapper<Resource> wrapper = new EntityWrapper<Resource>(resource);
        wrapper.orderBy("seq");
        return resourceMapper.selectList(wrapper);
    }

    private List<Resource> selectByType(Integer type) {
        Resource resource = new Resource();
        resource.setResourceType(type);
        resource.setStatus(0);
        EntityWrapper<Resource> wrapper = new EntityWrapper<Resource>(resource);
        wrapper.orderBy("seq");
        return resourceMapper.selectList(wrapper);
    }
    
    @Override
    public List<Tree> selectAllMenu() {
        List<Tree> trees = new ArrayList<Tree>();
        // 查询所有菜单
        List<Resource> resources = this.selectByType(RESOURCE_MENU);
        if (resources == null) {
            return trees;
        }
        for (Resource resource : resources) {
            Tree tree = new Tree();
            tree.setId(resource.getId());
            tree.setPid(resource.getPid());
            tree.setText(resource.getName());
            tree.setIconCls(resource.getIcon());
            tree.setAttributes(resource.getUrl());
            tree.setState(resource.getOpened());
            trees.add(tree);
        }
        return trees;
    }
    
    @Override
    public List<Tree> selectAllTree() {
        // 获取所有的资源 tree形式，展示
        List<Tree> trees = new ArrayList<Tree>();
        List<Resource> resources = this.selectAllByStatus(0);
        if (resources == null) {
            return trees;
        }
        for (Resource resource : resources) {
            Tree tree = new Tree();
            tree.setId(resource.getId());
            tree.setPid(resource.getPid());
            tree.setText(resource.getName());
            tree.setIconCls(resource.getIcon());
            tree.setAttributes(resource.getUrl());
            tree.setState(resource.getOpened());
            trees.add(tree);
        }
        return trees;
    }
    
    @Override
    public List<Tree> selectTree(ShiroUser shiroUser) {
        List<Tree> trees = new ArrayList<Tree>();
        // shiro中缓存的用户角色
        Set<String> roles = shiroUser.getRoles();
        if (roles == null) {
            return trees;
        }
        // 如果有超级管理员权限
        if (roles.contains("admin")) {
            List<Resource> resourceList = this.selectByType(RESOURCE_MENU);
            if (resourceList == null) {
                return trees;
            }
            for (Resource resource : resourceList) {
                Tree tree = new Tree();
                tree.setId(resource.getId());
                tree.setPid(resource.getPid());
                tree.setText(resource.getName());
                tree.setIconCls(resource.getIcon());
                tree.setAttributes(resource.getUrl());
                tree.setOpenMode(resource.getOpenMode());
                tree.setState(resource.getOpened());
                trees.add(tree);
            }
            return trees;
        }
        // 普通用户
        List<Long> roleIdList = userRoleMapper.selectRoleIdListByUserId(shiroUser.getId());
        if (roleIdList == null) {
            return trees;
        }
        List<Resource> resourceLists = roleMapper.selectResourceListByRoleIdList(roleIdList);
        if (resourceLists == null) {
            return trees;
        }
        for (Resource resource : resourceLists) {
            Tree tree = new Tree();
            tree.setId(resource.getId());
            tree.setPid(resource.getPid());
            tree.setText(resource.getName());
            tree.setIconCls(resource.getIcon());
            tree.setAttributes(resource.getUrl());
            tree.setOpenMode(resource.getOpenMode());
            tree.setState(resource.getOpened());
            trees.add(tree);
        }
        return trees;
    }

	@Override
	public boolean deleteById(Serializable resourceId) {
		roleResourceMapper.deleteByResourceId(resourceId);
		return super.deleteById(resourceId);
	}
}