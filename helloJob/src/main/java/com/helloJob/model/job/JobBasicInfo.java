package com.helloJob.model.job;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

@TableName("job_basic_info")
@Data
public class JobBasicInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键id */
	@TableId(type = IdType.AUTO)
	private Long id;
	private String jobName;//名称
	private String command;//执行命令
	private Long jobType;// 作业类型
	private Long creater;// 创建人
	private String remark;//备注
	private Integer hostId;
/*	private String jobUser;
	private String passwd;
	private String ip;*/
	private String createTime;
}
