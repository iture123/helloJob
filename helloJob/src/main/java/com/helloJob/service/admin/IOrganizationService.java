package com.helloJob.service.admin;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.helloJob.commons.result.Tree;
import com.helloJob.model.admin.Organization;

/**
 *
 * Organization 表数据服务层接口
 *
 */
public interface IOrganizationService extends IService<Organization> {

    List<Tree> selectTree();

    List<Organization> selectTreeGrid();

}