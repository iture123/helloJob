package com.helloJob.model.admin;

import java.io.Serializable;

import com.helloJob.commons.utils.JsonUtils;

/**
 *
 * 角色资源
 *
 */
public class RoleResource implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键id */
	private Long id;

	/** 角色id */
	private Long roleId;

	/** 资源id */
	private Long resourceId;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
