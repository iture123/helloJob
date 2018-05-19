package com.helloJob.model.admin;

import java.io.Serializable;
import java.util.Date;

import com.helloJob.commons.utils.JsonUtils;

import lombok.Data;

/**
 *
 * 用户
 *
 */
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键id */
	private Long id;

	/** 登陆名 */
	private String loginName;

	/** 用户名 */
	private String name;

	/** 密码 */
	private String password;
	
	/** 密码加密盐 */
	private String salt;

	/** 性别 */
	private Integer sex;

	/** 手机号 */
	private String phone;
	
	/** 邮箱地址 */
	private String email;

	/** 用户类别 */
	private Integer userType;

	/** 用户状态 */
	private Integer status;

	/** 所属机构 */
	private Integer organizationId;

	/** 创建时间 */
	private Date createTime;


	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
