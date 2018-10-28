package com.helloJob.jobExecutor;

import java.util.List;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.model.job.ScheBasicInfo;
import com.helloJob.model.job.ScheRelyJob;
import com.helloJob.service.job.JobBasicInfoService;
import com.helloJob.service.job.JobInstanceService;
import com.helloJob.service.job.JobOwnerService;
import com.helloJob.service.job.ScheBasicInfoService;
import com.helloJob.service.job.ScheRelyJobService;
import com.helloJob.utils.ApplicationContextUtil;
import com.helloJob.utils.EmailUtils;
import com.helloJob.utils.ThreadUtils;
import com.helloJob.vto.JobExecResult;

public abstract class AbstractJobExecutor implements Runnable {
	protected static final Logger log = LoggerFactory.getLogger(AbstractJobExecutor.class.getName());
	private JobBasicInfo job;
	protected Integer dt;
	private JobBasicInfoService jobService;
	private JobInstanceService jobInstanceService;
	private ScheBasicInfo scheInfo;
	private ScheRelyJobService scheRelyJobService;
	private ScheBasicInfoService scheBasicInfoService;
	private JobOwnerService jobOwnerService;
	private ApplicationContext context;
	public AbstractJobExecutor(JobBasicInfo job,ScheBasicInfo scheInfo, Integer dt) {
		this.job = job;
		this.dt = dt;
		this.context = ApplicationContextUtil.getContext();
		this.jobService = context.getBean(JobBasicInfoService.class);
		this.jobInstanceService = context.getBean(JobInstanceService.class);
		this.scheRelyJobService = context.getBean(ScheRelyJobService.class);
		this.scheBasicInfoService = context.getBean(ScheBasicInfoService.class);
		this.jobOwnerService = context.getBean(JobOwnerService.class);
		this.scheInfo = scheInfo;
	}

	@Override
	public void run() {
		log.info("判断作业是否可以执行:"+JSON.toJSONString(job));
		Long jobId = job.getId();
		Integer tryCount = scheInfo.getTryCount();
		for(int i= 0;i<=tryCount;i++){
			try {
				  if ("是".equals(scheInfo.getIsSelfRely())) {
						boolean isSelfRely = jobInstanceService.isSelfRely(jobId, dt);
						// 自依赖成功
						if (isSelfRely) {
							executeJob(job);
						}else{
							throw new RuntimeException(jobId+"自依赖失败。dt="+dt);
						}
					} else {
						executeJob(job);
					}
				break;

			} catch (Exception e) {
				int runCount = i+1;
				log.error(job.getJobName()+"("+job.getId()+")执行第"+runCount+"次失败,失败尝试次数为"+tryCount+"次");
				//发送告警邮件
				if(tryCount==i) {
					sendWarnEmail(job,dt,e.getMessage());
				}else {
					Integer tryInterval =  scheInfo.getTryInterval();
					ThreadUtils.sleeep(tryInterval*60*1000);
				}
			}
		}
		

	}
	public abstract JobExecResult execute(JobBasicInfo job) throws Exception;

	public void executeJob(JobBasicInfo job) throws Exception{
		jobInstanceService.delete(job.getId(), dt);
		JobExecResult result = execute(job);
		if (result.isSuccess()) {
			JobInstanceService jobInstanceService= context.getBean(JobInstanceService.class);
			jobInstanceService.add(job.getId(),dt);
			log.info("作业"+job.getId()+"执行成功。。查看是否有下一级的作业依赖");
			 List<ScheRelyJob> scheRelyJobList = scheRelyJobService.getTriggerJobList(job.getId());
			log.info(job.getId()+"的下级作业有："+JSON.toJSONString(scheRelyJobList));
			for (ScheRelyJob relyJob : scheRelyJobList) {
				JobBasicInfo childJob = jobService.get(relyJob.getJobId());
				CommonJobEntry.execute(childJob,scheBasicInfoService.getScheInfo(relyJob.getJobId()), dt);
			}
		}else {
			throw new RuntimeException(result.getLog());
		}
	}
	private void sendWarnEmail(JobBasicInfo job,Integer dt,String ex) {
		//String receiver = scheInfo.getReceiver();
		List<String> owners = jobOwnerService.getOwnerEmailByJobId(job.getId());
		if(owners.size() ==0) {
			log.warn("作业"+job.getId()+"责任人未配置邮箱地址 ！");
			return;
		}
		String content = "报错作业信息如下<br>作业名称："+job.getJobName()+"<br>编号："+job.getId()+"<br>执行命令："+job.getCommand()+"<br>dt："+dt;
		content += "<br><br>"+ex;
		String title ="调度系统作业告警："+job.getId();
		try {
			int ownersCount = owners.size();
			if(ownersCount <= 2) {
				EmailUtils.sendByHtml( String.join(",", owners), title , content);
			}else {
				String receiver = String.join(",", owners.subList(0, 2));
				String cc =  String.join(",", owners.subList(2,ownersCount));
				EmailUtils.sendByHtml(receiver ,cc, title , content);
			}
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
