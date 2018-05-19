package com.helloJob.controller.job;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helloJob.commons.base.BaseController;
import com.helloJob.commons.result.Tree;
import com.helloJob.service.job.ScheRelyJobService;

@Controller
@RequestMapping("/scheRelyJob")
public class ScheRelyJobController extends BaseController{
	@Autowired
	private ScheRelyJobService scheRelyJobService;
	@ResponseBody
	@RequestMapping("/getTreeList")
	public Object getTreeList(@RequestParam Long jobId) {
		List<Tree> treeList = scheRelyJobService.getTreeList(jobId);
		if(CollectionUtils.isEmpty(treeList)) {
			return renderError("作业不存在 ！");
		}
		return renderSuccess(treeList);
	}
}
