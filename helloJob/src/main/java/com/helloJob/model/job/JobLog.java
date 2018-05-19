package com.helloJob.model.job;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;


@TableName("job_log")
@Data
public class JobLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键id */
	@TableId(type = IdType.UUID)
	private String id;
	private Long jobId;
	private String applicationId;
	private String jobState;
	private Integer dt;
	private String beginTime;
	private String endTime;
	private String log;
	private String jobImg;
}
