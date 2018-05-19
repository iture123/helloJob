package com.helloJob.model.admin.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.helloJob.commons.utils.JsonUtils;
import com.helloJob.model.admin.Role;
import com.helloJob.model.admin.User;

import lombok.Data;

/**
 * @description：UserVo
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
@Data
public class UserVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;

	@NotBlank
	@Length(min = 4, max = 64)
	private String loginName;

	private String name;

	@JsonIgnore
	private String password;
	@JsonIgnore
	private String salt; // 密码加密盐

	private Integer sex;

	private String email;

	private Integer userType;

	private Integer status;

	private Integer organizationId;

	private Date createTime;

	private String phone;

	private List<Role> rolesList;

	private String organizationName;

	private String roleIds;

	private Date createdateStart;
	private Date createdateEnd;

	
	/**
	 * 比较vo和数据库中的用户是否同一个user，采用id比较
	 * @param user 用户
	 * @return 是否同一个人
	 */
	public boolean equalsUser(User user) {
		if (user == null) {
			return false;
		}
		Long userId = user.getId();
		if (id == null || userId == null) {
			return false;
		}
		return id.equals(userId);
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}