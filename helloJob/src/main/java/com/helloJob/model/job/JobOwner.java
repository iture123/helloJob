package com.helloJob.model.job;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
/**
 * 作业责任人
 * @author iture
 *
 */
@TableName("job_owner")
@Data
public class JobOwner {
	private Long jobId;
	private Long userId;
}
