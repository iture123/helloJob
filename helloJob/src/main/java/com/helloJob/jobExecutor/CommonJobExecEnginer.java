package com.helloJob.jobExecutor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.helloJob.constant.ScheTypeConst;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.model.job.ScheBasicInfo;
import com.helloJob.model.job.ScheRelyJob;
import com.helloJob.service.job.JobBasicInfoService;
import com.helloJob.service.job.JobInstanceService;
import com.helloJob.service.job.ScheBasicInfoService;
import com.helloJob.service.job.ScheRelyJobService;
import com.helloJob.utils.ApplicationContextUtil;
import com.helloJob.utils.DateUtils;
import com.helloJob.utils.job.JobThreadPool;
import com.helloJob.vto.CommonResult;
/**
 * 作业执行器，作业执行入口
 * @author iture
 *
 */
public class CommonJobExecEnginer implements Runnable{
	public static final Logger log = LoggerFactory.getLogger(CommonJobExecEnginer.class.getName());
	
	private JobBasicInfo job;
	private ScheBasicInfo scheInfo;
	private int dt;
	private ScheRelyJobService scheRelyJobService;
	private JobInstanceService jobInstanceService;
	private ScheBasicInfoService scheBasicInfoService;
	private JobBasicInfoService jobBasicInfoService;
	public CommonJobExecEnginer(JobBasicInfo job,ScheBasicInfo scheInfo,int dt) {
		this.job = job;
		this.scheInfo = scheInfo;
		this.dt = dt;
		ApplicationContext context = ApplicationContextUtil.getContext();
		this.jobInstanceService=  context.getBean(JobInstanceService.class);	
		this.scheRelyJobService = context.getBean(ScheRelyJobService.class);
		this.scheBasicInfoService = context.getBean(ScheBasicInfoService.class);
		this.jobBasicInfoService = context.getBean(JobBasicInfoService.class);
	}
	/**
	 * 判断是否满足执行条件
	 * @return
	 */
	private  CommonResult isCanBeExec(JobBasicInfo job,ScheBasicInfo scheInfo,Integer dt) {
		Long jobId = job.getId();
		ApplicationContext context = ApplicationContextUtil.getContext();
		Integer beginTime = scheInfo.getBeginTime();
		Integer endTime = scheInfo.getEndTime();
		Integer toDay = DateUtils.getYyyyMMdd();
		if(beginTime !=null && toDay >=beginTime ) {
			String msg = "未执行调用作业！<br>未到作业执行开始时间 ！开始时间为："+beginTime;
			return new CommonResult(false, msg);
		}
		if(endTime !=null && endTime <= toDay){
			String msg = "未执行调用作业！<br>超出作业执行结束时间 ！结束时间为："+endTime;
			return new CommonResult(false, msg);
		}
		if ("是".equals(scheInfo.getIsSelfRely())) {
			boolean isSelfRely = context.getBean(JobInstanceService.class).isSelfRely(jobId, dt);
			// 自依赖失败
			if (! isSelfRely) {
				return new CommonResult(false, "自依赖失败！dt=" + dt);
			} 
		} 
		//有依赖上级作业的
		if(ScheTypeConst.RELY_PRE_JOB.equals(scheInfo.getScheType())) {
			JobInstanceService jobInstanceService = context.getBean(JobInstanceService.class);
			List<Long> relyJobFailInstanceList = jobInstanceService .getRelyJobFailInstance(job.getId(),dt);
			// 依赖的上一级有未成功的
			if (relyJobFailInstanceList.size() >= 0) {
				return new CommonResult(false, String.format("依赖的上级作业％s尚未成功！", relyJobFailInstanceList.toString()));
			}
		}
		return new CommonResult(true,null);
	}
	/**
	 * 执行作业树
	 * @param job
	 * @param scheInfo
	 * @param dt
	 */
	private  void execute(JobBasicInfo job,ScheBasicInfo scheInfo,Integer dt) {
			CommonResult result = isCanBeExec(job, scheInfo, dt);
			if(result.isSuccess()) {
				log.info("作业被调起："+JSON.toJSONString(job));
				AbstractExecutor jobExecutor = ExecutorFactory.make(job, dt);
				boolean execResult = jobExecutor.exec(job.getCommand());
				if(execResult) {
					jobInstanceService.add(job.getId(),dt);
					log.info("作业"+job.getId()+"执行成功。。查看是否有下一级的作业依赖");
					 List<ScheRelyJob> scheRelyJobList = scheRelyJobService.getTriggerJobList(job.getId());
					log.info(job.getId()+"的下级作业有："+JSON.toJSONString(scheRelyJobList));
					for (ScheRelyJob relyJob : scheRelyJobList) {
						JobBasicInfo childJob = jobBasicInfoService.get(relyJob.getJobId());
						JobThreadPool.getInstance().execute(new CommonJobExecEnginer(childJob, scheBasicInfoService.getScheInfo(relyJob.getJobId()), dt));
					}
				}
				
			}else {
				log.warn(job.getId()+":"+result.getMsg());
			}
	}
	@Override
	public void run() {
		execute(this.job, this.scheInfo, this.dt);
	}
}
