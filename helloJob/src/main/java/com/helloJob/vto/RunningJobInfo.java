package com.helloJob.vto;

import com.jcraft.jsch.ChannelExec;

import lombok.Data;

@Data
public class RunningJobInfo {
	private ChannelExec channelExec;
	private String firstLineLog="";//
	private Integer exitVal = 1;


	
}
