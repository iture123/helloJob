package com.helloJob.controller.job;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.helloJob.commons.base.BaseController;
import com.helloJob.constant.ScheTypeConst;
import com.helloJob.model.job.ScheBasicInfo;
import com.helloJob.service.job.JobInstanceService;
import com.helloJob.service.job.ScheBasicInfoService;
import com.helloJob.service.job.ScheRelyJobService;
import com.helloJob.utils.DateUtils;
import com.helloJob.utils.ThreadUtils;
/**
 * 作业调度
 * @author iture
 *
 */
@Controller
@RequestMapping("/sche")
public class ScheduleController extends BaseController {
	
	@Autowired
	private ScheRelyJobService scheRelyJobService;
	@Autowired
	private ScheBasicInfoService scheBasicInfoService;
	@Autowired
	private JobInstanceService jobInstanceService;
	/**
	 * 挂载调度
	 * **/
	@ResponseBody
	@RequestMapping("/mount")
	public Object mountSche(@RequestParam(required=true) Long jobId,
			@RequestParam(required=false) String cron,
			@RequestParam(value = "relyPreJob[]",required=false) Long[] relyPreJob,ScheBasicInfo scheInfo
			){
		logger.info("scheInfo:"+JSON.toJSONString(scheInfo));
		try{
			if(scheBasicInfoService.getScheInfo(jobId) !=null) {
				return renderError("该作业存在调度配置，请先停用 ！");
			}
			scheInfo.setCreater(getUserId());
			scheInfo.setCreateTime(DateUtils.getCreateTime());
			String scheType = scheInfo.getScheType();
			if(ScheTypeConst.TIME_SCHE.equals(scheType)){
				scheBasicInfoService.mountScheByTime(jobId,cron);
			}else if( ScheTypeConst.RELY_PRE_JOB.equals(scheType)){
				//判断是否形成了一个环，避免死循环
				Set<Long> allTriggerJobs = scheRelyJobService.getAllTriggerJobs(jobId);
				for(Long id : relyPreJob) {
					if(allTriggerJobs.contains(id)) {
						return renderError("与作业"+id+"构成了死循环 ！");
					}
				}
				scheInfo.setCron(Strings.join(Lists.newArrayList(relyPreJob),','));
				scheRelyJobService.save(jobId, relyPreJob);
			}
			scheBasicInfoService.save(scheInfo);
			return renderSuccess("挂载调度成功 ！");
		}catch(Exception e){
			e.printStackTrace();
			return renderError(e.getMessage());
		}
	}
	/**
	 * 停用调度
	 * **/
	@ResponseBody
	@RequestMapping("/stopSchedule")
	public Object stopSchedule(@RequestParam Long jobId){
		try{
			ScheBasicInfo scheInfo = scheBasicInfoService.getScheInfo(jobId);
			String scheType = scheInfo.getScheType();
			if(ScheTypeConst.TIME_SCHE.equals(scheType)){
				scheBasicInfoService.stopTimeSchedule(jobId);
			}else if(ScheTypeConst.RELY_PRE_JOB.equals(scheType)){
				scheBasicInfoService.stopRelyJobSchedule(jobId);
			}else {
				throw new RuntimeException("调度类型不存在 ！停用失败 !");
			}
			return renderSuccess("停用调度成功 ！");
		}catch(Exception e){
			e.printStackTrace();
			return renderError(e.getMessage());
		}
	}
	/**
	 * 运行一次
	 * 将会kill 掉该作业和子孙作业正在运行的实例
	 * 将会从实例成功表（job_instace）删除该作业和子作业的记录
	 * ***/
	@RequestMapping("/runOnce")
	@ResponseBody
	public Object runOnce(@RequestParam long jobId,@RequestParam Integer dt,@RequestParam(defaultValue="否") String isSelfRely){
		try {
			if(StringUtils.isEmpty(dt)) {
				dt = DateUtils.getYesterday();
			}
			if("是".equals(isSelfRely)) {
				//判断自依赖是否ok
				if(! jobInstanceService.isSelfRely(jobId, dt)){
					throw new RuntimeException("操作失败！作业"+jobId+"自依赖失败。dt="+dt);
				}
			}
			List<Long> relyJobFailInstanceList = jobInstanceService.getRelyJobFailInstance(jobId,dt);
			if(relyJobFailInstanceList.size() == 0) {
				//kill掉子孙作业作业正在运行的实例
				Set<Long> allTriggerJobs = scheRelyJobService.getAllTriggerJobs(jobId);
				allTriggerJobs.add(jobId);
				logger.info("将要kill掉的作业:"+allTriggerJobs);
				String firstLineLog = "<div style='color:red'>"+DateUtils.getCreateTime()+ getStaffName()+"点击运行一次作业"+jobId+"，引起其子孙作业被kill掉 !</div><br>";
				scheBasicInfoService.killJobs(allTriggerJobs,dt,firstLineLog);
				jobInstanceService.delete(allTriggerJobs, dt);
				scheBasicInfoService.runOnce(jobId, dt,isSelfRely);
				ThreadUtils.sleeep(300);
				return renderSuccess();
			}else {
				throw new RuntimeException("操作失败！作业"+jobId+"依赖的作业"+ JSON.toJSONString(relyJobFailInstanceList)+"尚未成功。dt="+dt);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return renderError(e.getMessage());
		}
	
	
	}
	
}
