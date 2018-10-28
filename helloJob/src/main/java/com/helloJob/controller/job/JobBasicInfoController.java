package com.helloJob.controller.job;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.helloJob.commons.base.BaseController;
import com.helloJob.commons.result.PageInfo;
import com.helloJob.model.job.HostInfo;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.model.job.ScheBasicInfo;
import com.helloJob.service.job.HostInfoService;
import com.helloJob.service.job.JobBasicInfoService;
import com.helloJob.service.job.ScheBasicInfoService;
import com.helloJob.service.job.ScheRelyJobService;
import com.helloJob.utils.DateUtils;
import com.helloJob.vto.ComboboxVto;

@Controller
@RequestMapping("/job")
public class JobBasicInfoController  extends BaseController{
	@Autowired
	private JobBasicInfoService jobBasicInfoService;
	@Autowired
	private ScheBasicInfoService scheBasicInfoService;
	@Autowired
	private ScheRelyJobService scheRelyJobService;
	@Autowired
	private HostInfoService HostInfoService;
	
	@GetMapping("/jobBasicInfo")
	public String jobBasicInfo() {
		return "job/jobBasicInfo";
	}
	/** 
	 * 添加作业基本信息
	 * **/
	@ResponseBody
	@RequestMapping("/add")
	public Object add(JobBasicInfo job){
		logger.info("添加作业job:"+JSON.toJSONString(job));
		try {
			job.setCommand(StringEscapeUtils.unescapeHtml(job.getCommand()));
			job.setCreater(getUserId());
			job.setCreateTime(DateUtils.getCreateTime());
			jobBasicInfoService.save(job);
			return renderSuccess();
		}catch(Exception e) {
			e.printStackTrace();
			return renderError(e.getMessage());
		}
	}
	@ResponseBody
	@RequestMapping("/get")
	public Object get(Long jobId){
		JobBasicInfo job = jobBasicInfoService.get(jobId);
		Map<String,Object> dataMap = Maps.newHashMap();
		dataMap.put("job", job);
		ScheBasicInfo scheBasicInfo = scheBasicInfoService.getScheInfo(jobId);
		HostInfo hostInfo = HostInfoService.get(job.getHostId());
		hostInfo.setPasswd(null);
		dataMap.put("scheBasicInfo",scheBasicInfo);
		dataMap.put("hostInfo", hostInfo);
		return renderSuccess(dataMap);
	}
	@ResponseBody
	@RequestMapping("/getJobInfoList")
	public Object getJobInfoList(Integer page, Integer rows, 
            @RequestParam(value = "sort", defaultValue = "create_time") String sort, 
            @RequestParam(value = "order", defaultValue = "DESC") String order,
            Long jobId,Long creater,Long jobType,String jobName){
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		Map<String, Object> condition = Maps.newHashMap();
		condition.put("jobId", jobId);
		condition.put("creater", creater);
		condition.put("jobType", jobType);
		condition.put("jobName", jobName);
		pageInfo.setCondition(condition );
		jobBasicInfoService.getJobInfoList(pageInfo);
		return pageInfo;
	}
	/**
	 * 获取上一级作业，用于配置作业依赖
	 * */
	@RequestMapping("/getPreJobList")
	@ResponseBody
	public Object getPreJobList(@RequestParam Long jobId,@RequestParam(required=false) String jobInfo){
		 List<JobBasicInfo> list = jobBasicInfoService.getPreJobList(jobId, jobInfo);
		return renderSuccess(list);
	}
	/**
	 * 删除一个作业
	 * */
	@RequestMapping("delJob")
	@ResponseBody
	public Object delJob(@RequestParam Long jobId ) {
		try {
			JobBasicInfo job = jobBasicInfoService.get(jobId);
			if(job == null) {
				throw new RuntimeException("作业不存在 ！");
			}
			ScheBasicInfo scheInfo = scheBasicInfoService.getScheInfo(jobId);
			if(scheInfo != null) {
				throw new RuntimeException("请先停掉改作业的调度 ！");
			}
			List<Long> triggerJobList = scheRelyJobService.getTriggerJobs(jobId);
			if(CollectionUtils.isNotEmpty(triggerJobList)) {
				throw new RuntimeException("请先停掉作业"+JSON.toJSONString(triggerJobList)+"对本作业的依赖！");
			}
			jobBasicInfoService.delJob(jobId);
			return renderSuccess();
		}catch(Exception e) {
			e.printStackTrace();
			return renderError(e.getMessage());
		}
	}
@RequestMapping("update")
	@ResponseBody
	public Object update(JobBasicInfo job){
		logger.info(getStaffName()+"更新作业信息:"+JSON.toJSONString(job));
		job.setCommand(StringEscapeUtils.unescapeHtml(job.getCommand()));
		job.setCreater(getUserId());
		job.setCreateTime(DateUtils.getCreateTime());
		jobBasicInfoService.update(job);
		return renderSuccess();
	}
@RequestMapping("getHasJobUserList")
@ResponseBody
	public Object getHasJobUserList() {
		List<ComboboxVto> boxList = Lists.newArrayList();
		boxList.add(new ComboboxVto("","全部"));
		List<ComboboxVto> dataList = jobBasicInfoService.getHasJobUserList();
		boxList.addAll(dataList);
		return boxList;
	}
}
