package com.helloJob.executor;

import com.helloJob.service.job.JobLogService;
import com.helloJob.utils.ApplicationContextUtil;
import com.helloJob.utils.ThreadUtils;
/**
 * 作业日志监控类
 * @author iture
 *
 */
public class LogMonitorThread implements Runnable{
	private String jobLogId;
	private StringBuffer log = new StringBuffer();
	private boolean isExit = false;
	private JobLogService jobLogService;
	public LogMonitorThread(String jobLogId,StringBuffer log) {
		this.jobLogId=jobLogId;
		this.log = log;
		this.jobLogService = ApplicationContextUtil.getContext().getBean(JobLogService.class);
	}
	@Override
	public void run() {
		int lastLength = log.length();
		while(true) {
			if(lastLength != log.length()) {
				//新写入了日志
				jobLogService.updateLog(jobLogId, log.toString());
				lastLength = log.length();
			}
			ThreadUtils.sleeep(2000);
			if(this.isExit) {
				if(lastLength != log.length()) {
					jobLogService.updateLog(jobLogId, log.toString());
				}
				break;
			}
		}
	}
	public void exitThread() {
		this.isExit = true;
	}
}
