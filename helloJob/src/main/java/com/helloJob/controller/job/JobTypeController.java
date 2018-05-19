package com.helloJob.controller.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helloJob.commons.base.BaseController;
import com.helloJob.commons.result.PageInfo;
import com.helloJob.model.job.JobType;
import com.helloJob.service.job.JobTypeService;
import com.helloJob.utils.DateUtils;

@Controller()
@RequestMapping("/jobType")
public class JobTypeController extends BaseController {
	@Autowired
	private JobTypeService jobTypeService;
	@RequestMapping("/jobType")
	public String jobType() {
		return "/job/jobType";
	}
	@RequestMapping("/add")
	@ResponseBody
	public Object add(@RequestParam String name,@RequestParam Integer seq) {
		JobType jobType = new JobType();
		jobType.setName(name);
		jobType.setSeq(seq);
		jobType.setCreateTime(DateUtils.getCreateTime());
		jobTypeService.add(jobType );
		return renderSuccess();
	}
	@RequestMapping("/update")
	@ResponseBody
	public Object update(JobType jobType ) {
		jobType.setCreateTime(DateUtils.getCreateTime());
		jobTypeService.update(jobType );
		return renderSuccess();
	}
	@ResponseBody
	@RequestMapping("/grid")
	public Object grid(Integer page, Integer rows, 
            @RequestParam(value = "sort", defaultValue = "create_time") String sort, 
            @RequestParam(value = "order", defaultValue = "DESC") String order){
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		jobTypeService.grid(pageInfo);
		return pageInfo;
	}
	@ResponseBody
	@RequestMapping("/combobox")
	public Object combobox() {
		return jobTypeService.combobox();
	}
}
