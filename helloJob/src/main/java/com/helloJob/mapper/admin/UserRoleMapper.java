package com.helloJob.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.helloJob.model.admin.UserRole;

/**
 *
 * UserRole 表数据库控制层接口
 *
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> selectByUserId(@Param("userId") Long userId);

    @Select("select role_id AS roleId from user_role where user_id = #{userId}")
    @ResultType(Long.class)
    List<Long> selectRoleIdListByUserId(@Param("userId") Long userId);

    @Delete("DELETE FROM user_role WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);

}