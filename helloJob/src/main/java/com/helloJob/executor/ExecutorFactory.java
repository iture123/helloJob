package com.helloJob.executor;

import com.helloJob.constant.HostConst;
import com.helloJob.executor.impl.LocalExecutor;
import com.helloJob.executor.impl.SshExector;
import com.helloJob.model.job.JobBasicInfo;

public class ExecutorFactory {
	public static AbstractExecutor make(JobBasicInfo job, Integer dt) {
		int hostId = job.getHostId();
		if(hostId == HostConst.LOCALHOST) {
			return new LocalExecutor(job, dt);
		}else if(hostId == HostConst.SSH) {
			return new SshExector(job, dt);
		}else {
			System.out.println("host id is "+hostId);
			return null;
		}
		
	}
}
