package com.helloJob.utils.job;

import com.helloJob.model.job.JobLog;
import com.helloJob.service.job.JobLogService;
import com.helloJob.utils.ApplicationContextUtil;
import com.helloJob.utils.ThreadUtils;

public class MonitorLogChange implements Runnable {
	JobLog jobLog;
	StringBuffer sb;
//	int exitVal = 1; //1执行中，0成功，-1失败
	JobLogService jobLogService;
	public  MonitorLogChange(JobLog jobLog,StringBuffer sb) {
		this.jobLog = jobLog;
		this.sb = sb;
		this.jobLogService = ApplicationContextUtil.getContext().getBean(JobLogService.class);
	}
	@Override
	public void run() {
		String oldSb = sb.toString();
		while(true) {
			if(  ! oldSb.toString().equals(sb.toString())) {
				//更新日志
				oldSb = sb.toString();
				jobLog.setLog(oldSb.toString());
				jobLogService.update(jobLog);
			}
			ThreadUtils.sleeep(2000);
		}
	}
}
