package com.helloJob.model.job;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

/**
 *调度依赖的父类
 * */
@TableName("sche_rely_job")
@Data
public class ScheRelyJob {
	private Long pid;
	private Long jobId;
}
