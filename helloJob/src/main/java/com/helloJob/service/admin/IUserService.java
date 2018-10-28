package com.helloJob.service.admin;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.helloJob.commons.result.PageInfo;
import com.helloJob.model.admin.User;
import com.helloJob.model.admin.vo.UserVo;

/**
 *
 * User 表数据服务层接口
 *
 */
public interface IUserService extends IService<User> {

    List<User> selectByLoginName(UserVo userVo);

    void insertByVo(UserVo userVo);

    UserVo selectVoById(Long id);

    void updateByVo(UserVo userVo);

    void updatePwdByUserId(Long userId, String md5Hex);

    void selectDataGrid(PageInfo pageInfo);

    void deleteUserById(Long id);

    List<Map<String,Object>> getAllUserList();
    /**
     * 获取其他用户
     * @param userId
     * @return
     */
    List<User> getOtherUsers(long userId);
}