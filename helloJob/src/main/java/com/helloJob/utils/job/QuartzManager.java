package com.helloJob.utils.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.helloJob.jobExecutor.QuartzJobEntry;

/**
 * @Description: 定时任务管理类
 * 
 * @ClassName: QuartzManager
 * @Copyright: Copyright (c) 2014
 * 
 * @author Comsys-LZP
 * @date 2014-6-26 下午03:15:52
 * @version V2.0
 */
public class QuartzManager {
	public static final Logger log = LoggerFactory.getLogger(QuartzManager.class.getName());
	private static Scheduler sched = null;
	public static void Init(ApplicationContext  ac){
		sched  = ac.getBean("schedulerFactoryBean", StdScheduler.class);
	}
	public static void Init(){
		 SchedulerFactory schedulerfactory=new StdSchedulerFactory();  
		 try {
			 sched = schedulerfactory.getScheduler();
			 sched.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
	public static void addJob(Long jobId,String time) {
		addJob(jobId+"",time);
	}
	/**
	 * 添加作业
	 * */
	public static void addJob(String jobId,  String time) {
		log.info("添加作业"+jobId);
		JobDetail job = JobBuilder.newJob(QuartzJobEntry.class).withIdentity(jobId, JOB_GROUP_NAME).build();
		CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(time);
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobId, TRIGGER_GROUP_NAME)
				.withSchedule(schedBuilder).build();
		try {
			sched.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * 删除作业
	 * @throws SchedulerException 
	 * **/
	public static void scheBasicInfoMapper(String jobId)  {
			try {
				sched.pauseTrigger(getTriggerKey(jobId));
				sched.unscheduleJob(getTriggerKey(jobId));// 移除触发器
				sched.deleteJob(getJobKey(jobId));
			} catch (SchedulerException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}// 删除任务
	}
	/**
	 * 修改作业
	 * **/
	public static void modifyJobTime(String jobId, String time) {
		CronTrigger trigger;
		try {
			trigger = (CronTrigger) sched.getTrigger(getTriggerKey(jobId));
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				removeJob(jobId);
				addJob(jobId, time);
			}
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 删除作业
	 * @throws SchedulerException 
	 * **/
	public static void removeJob(String jobId)  {
			try {
				sched.pauseTrigger(getTriggerKey(jobId));
				sched.unscheduleJob(getTriggerKey(jobId));// 移除触发器
				sched.deleteJob(getJobKey(jobId));
			} catch (SchedulerException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}// 删除任务
	}
	private static TriggerKey getTriggerKey(String jobName) {
		TriggerKey key = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
		return key;
	}

	private static JobKey getJobKey(String jobName) {
		JobKey key = new JobKey(jobName, JOB_GROUP_NAME);
		return key;
	}
	
}
