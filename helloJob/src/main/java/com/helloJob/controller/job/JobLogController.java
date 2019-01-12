package com.helloJob.controller.job;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.helloJob.commons.base.BaseController;
import com.helloJob.commons.result.PageInfo;
import com.helloJob.commons.result.Result;
import com.helloJob.constant.JobStateConst;
import com.helloJob.jobExecutor.RunningExectorUtils;
import com.helloJob.model.job.JobLog;
import com.helloJob.service.job.JobInstanceService;
import com.helloJob.service.job.JobLogService;
import com.helloJob.utils.DateUtils;
import com.helloJob.utils.ThreadUtils;

@Controller
@RequestMapping("/jobLog")
public class JobLogController extends BaseController{
	@Autowired
	private JobLogService jobLogService ;
	@Autowired
	JobInstanceService jobInstanceService;
	
	@RequestMapping("/jobLog")
	public String jobLog() {
		return "/job/jobLog";
	}
	@ResponseBody
	@RequestMapping("/grid")
	public Object grid(Integer page, Integer rows, 
            @RequestParam(value = "sort", defaultValue = "create_time") String sort, 
            @RequestParam(value = "order", defaultValue = "DESC") String order,Long jobId,Long dt,String jobState){
		if("".equals(jobState)) jobState =null;
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		Map<String,Object> condition = Maps.newHashMap();
		condition.put("jobId", jobId);
		condition.put("dt", dt);
		condition.put("jobState", jobState);
		pageInfo.setCondition(condition);
		jobLogService.grid(pageInfo);
		return pageInfo;
	}
	@ResponseBody
	@RequestMapping("/seeLog")
	public Object seeLog(@RequestParam String jobLogId) {
		String log = jobLogService.seeLog(jobLogId);
	    Result result = new Result();
        result.setSuccess(true);
        result.setMsg("操作成功！");
        result.setObj(log);
		return result;
	}
	
	/**
	 * 人为设置作业成功 
	 * ***/
	@ResponseBody
	@RequestMapping("/setSuccess")
	public Object setSuccess(@RequestParam String jobLogId) {
			JobLog jobLog = jobLogService.get(jobLogId);
			if(jobLog == null) {
				return renderError("作业实例不存在 ！");
			}if(jobLog.getJobState().equals(JobStateConst.ERROR) || jobLog.getJobState().equals(JobStateConst.WARNING)) {
				String firstLineLog = "<div style='color:red'>"+DateUtils.getCreateTime()+":"+getStaffName()+"将该作业设为成功！</div><br>";
				jobLog.setLog(firstLineLog+jobLog.getLog());
				jobLogService.updateSuccess(jobLogId);
				jobInstanceService.add(jobLog.getJobId(), jobLog.getDt());
				return renderSuccess();
			}else {
				return renderError("该作业尚未结束！");
			}
	}
	/**
	 * kill掉作业
	 * **/
	@RequestMapping("/killJob")
	@ResponseBody
	public Object killJob(@RequestParam String jobLogId) {
		RunningExectorUtils.kill(jobLogId);
		/*
		 * RunningJobInfo runningJobInfo = RunningJobUtils.get(jobLogId);
		 * if(runningJobInfo !=null) { String firstLineLog =
		 * "<div style='color:red'>"+DateUtils.getCreateTime()+":该作业被"+getStaffName()
		 * +"kill 掉并设为失败！</div>"; runningJobInfo.setExitVal(-1);
		 * runningJobInfo.setFirstLineLog(firstLineLog);
		 * RunningExectorUtils.kill(jobLogId); waitJobStop(jobLogId); }else { return
		 * renderError("作业实例不存在 ！"); }
		 */
			return renderSuccess();
	}
	/***
	 * 等待作业结束
	 * **/
	private void waitJobStop(String jobLogId) {
		for(int i =0;i<10;i++) {
			String jobState = jobLogService.getJobState(jobLogId);
			if(! JobStateConst.RUNNING.equals(jobState)) break;
			else ThreadUtils.sleeep(200);
		}
	}
	/***
	 * 获取最新的作业日志
	 * **/
	@RequestMapping("/getJobState")
	@ResponseBody
	Object getJobState(@RequestParam Long jobId,@RequestParam Integer dt) {
		String jobState =  jobLogService.getJobState(jobId, dt);
		if(jobState != null) {
			return renderSuccess(jobState);
		}else {
			return renderError("作业日志不存在 ！");
		}
	}
}
