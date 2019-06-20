package com.helloJob.jobExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.helloJob.jobExecutor.impl.LocalExecutor;
import com.helloJob.jobExecutor.impl.SshExector;
import com.helloJob.model.job.HostInfo;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.service.job.HostInfoService;
import com.helloJob.utils.ApplicationContextUtil;

public class ExecutorFactory {
	public static final Logger log = LoggerFactory.getLogger(ExecutorFactory.class.getName());
	public static AbstractExecutor make(JobBasicInfo job, Integer dt) {
		ApplicationContext context = ApplicationContextUtil.getContext();
		HostInfoService hostInfoService = context.getBean(HostInfoService.class);	
		HostInfo hostInfo = hostInfoService.get(job.getHostId());
		//log.info(JSONObject.toJSONString(hostInfo));
		if(hostInfo.getProtocol().equals("本地执行")) {
			return new LocalExecutor(job, dt);
		}else if(hostInfo.getProtocol().equals("ssh")) {
			return new SshExector(job, dt);
		}else {
			return null;
		}
		
	}
}
