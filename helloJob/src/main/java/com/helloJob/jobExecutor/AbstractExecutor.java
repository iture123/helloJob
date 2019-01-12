package com.helloJob.jobExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.model.job.JobLog;
import com.helloJob.service.job.JobLogService;
import com.helloJob.utils.ApplicationContextUtil;

public abstract class AbstractExecutor {
	protected static final Logger log = LoggerFactory.getLogger(AbstractExecutor.class.getName());
	// 作业执行的日志写入该类
	protected StringBuffer logBuffer;
	protected JobBasicInfo job;
	private Integer dt;
	private JobLogService jobLogService;

	public AbstractExecutor(JobBasicInfo job,  Integer dt) {
		this.logBuffer =  new StringBuffer("<h3>执行命令:"+job.getCommand()+"</h3>");
		this.job = job;
		this.dt = dt;
		ApplicationContext context = ApplicationContextUtil.getContext();
		this.jobLogService = context.getBean(JobLogService.class);
		
	}

	public boolean exec(String command) {
		JobLog jobLog = jobLogService.addRunningLog(job.getId(), dt, job);
		String jobLogId = jobLog.getId();
		RunningExectorUtils.add(jobLogId, this);
		LogMonitorThread logMonitorThread = new LogMonitorThread(jobLogId, logBuffer);
		new Thread(logMonitorThread).start();
		try {
			boolean execResult = execCommand(command);
			if(execResult) {
				jobLogService.updateSuccess(jobLogId);
			}else {
				jobLogService.updateError(jobLogId);
			}
			return execResult;
		} catch (Exception ex) {
			return false;
		} finally {
			RunningExectorUtils.remove(jobLogId);
			logMonitorThread.exitThread();
		}
	}
	protected abstract boolean execCommand(String command);

	/**
	 * 中断执行
	 * 
	 * @return
	 */
	public abstract void interruptExec();

}
