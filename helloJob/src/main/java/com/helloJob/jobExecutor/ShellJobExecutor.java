package com.helloJob.jobExecutor;


import com.alibaba.fastjson.JSON;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.model.job.ScheBasicInfo;
import com.helloJob.utils.job.SshUtils;
import com.helloJob.vto.JobExecResult;

public class ShellJobExecutor extends AbstractJobExecutor{
	public ShellJobExecutor(JobBasicInfo job,ScheBasicInfo scheInfo,Integer dt) {
		super(job, scheInfo,dt);
	}
	@Override
	public JobExecResult execute(JobBasicInfo job) throws Exception {
		log.info(JSON.toJSONString(job));
		job.setCommand( job.getCommand().replace("${dt}",this.dt+""));
		JobExecResult result = SshUtils.execute(job,this.dt);
		return result;
	}
}
