package com.helloJob.model.admin;

import org.hibernate.validator.constraints.NotBlank;

import com.helloJob.commons.utils.JsonUtils;

import java.io.Serializable;

/**
 *
 * 角色
 *
 */
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键id */
	private Long id;

	/** 角色名 */
	@NotBlank
	private String name;

	/** 排序号 */
	private Integer seq;

	/** 简介 */
	private String description;

	/** 状态 */
	private Integer status;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
