package com.helloJob.service.admin;

import com.baomidou.mybatisplus.service.IService;
import com.helloJob.commons.result.PageInfo;
import com.helloJob.model.admin.SysLog;

/**
 *
 * SysLog 表数据服务层接口
 *
 */
public interface ISysLogService extends IService<SysLog> {

    void selectDataGrid(PageInfo pageInfo);

}