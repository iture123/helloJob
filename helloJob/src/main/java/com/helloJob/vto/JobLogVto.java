package com.helloJob.vto;

import lombok.Data;

@Data
public class JobLogVto {
	private String id;
	private Long jobId;
	private String jobState;
	private Integer dt;
	private String beginTime;
	private String endTime;
	private String jobImg;
	private String elapsedTime;//耗时
}
