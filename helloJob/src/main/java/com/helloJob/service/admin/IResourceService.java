package com.helloJob.service.admin;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.helloJob.commons.result.Tree;
import com.helloJob.commons.shiro.ShiroUser;
import com.helloJob.model.admin.Resource;

/**
 *
 * Resource 表数据服务层接口
 *
 */
public interface IResourceService extends IService<Resource> {

    List<Resource> selectAll();

    List<Tree> selectAllMenu();

    List<Tree> selectAllTree();

    List<Tree> selectTree(ShiroUser shiroUser);

}