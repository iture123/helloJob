package com.helloJob.model.job;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
@TableName("sche_basic_info")
@Data
public class ScheBasicInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type = IdType.INPUT,value="job_id")
	private Long jobId;
	private Long creater;//创建人
	private String scheType;
	private String  cron;//时间调度
	private String isSelfRely;//是否自依赖
	private Integer beginTime;
	private Integer endTime;
	private Integer tryCount = 0;//默认重试次数
	private Integer tryInterval = 1;//默认重试间隔分钟
	private String receiver;//收件人
	private String createTime;
	
}
