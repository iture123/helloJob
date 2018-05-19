package com.helloJob.jobExecutor;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.service.job.JobBasicInfoService;
import com.helloJob.service.job.ScheBasicInfoService;
import com.helloJob.utils.ApplicationContextUtil;
import com.helloJob.utils.DateUtils;
public class QuartzJobEntry implements org.quartz.Job{
	
	public static final Logger log = LoggerFactory.getLogger(QuartzJobEntry.class.getName());
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Long jobId = Long.parseLong(context.getJobDetail().getKey().getName());
		log.info("执行作业id="+jobId);
		JobBasicInfoService jobService = ApplicationContextUtil.getContext().getBean(JobBasicInfoService.class);
		ScheBasicInfoService scheBasicInfoService  = ApplicationContextUtil.getContext().getBean(ScheBasicInfoService.class);
		JobBasicInfo job = jobService.get(jobId);
		CommonJobEntry.execute(job,scheBasicInfoService.getScheInfo(jobId),DateUtils.getYesterday());
	}
}	
