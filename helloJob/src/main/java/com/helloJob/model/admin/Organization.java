package com.helloJob.model.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.helloJob.commons.utils.JsonUtils;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 组织机构
 *
 */
public class Organization implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键id */
	private Long id;

	/** 组织名 */
	@NotBlank
	private String name;

	/** 地址 */
	private String address;

	/** 编号 */
	@NotBlank
	private String code;

	/** 图标 */
	@JsonProperty("iconCls")
	private String icon;

	/** 父级主键 */
	private Long pid;

	/** 排序 */
	private Integer seq;

	/** 创建时间 */
	private Date createTime;

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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}
