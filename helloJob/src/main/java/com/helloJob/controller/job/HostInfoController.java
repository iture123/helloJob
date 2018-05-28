package com.helloJob.controller.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helloJob.commons.base.BaseController;
import com.helloJob.model.job.HostInfo;
import com.helloJob.service.job.HostInfoService;
@Controller
@RequestMapping("/host")
public class HostInfoController  extends BaseController{
	@Autowired
	private HostInfoService hostInfoService;
	@GetMapping("/hostInfo")
	public String hostInfo() {
		return "job/hostInfo";
	}
	@ResponseBody
	@RequestMapping("/add")
	public Object add(HostInfo hostInfo) {
		hostInfoService.add(hostInfo);
		return renderSuccess();
	}
	@ResponseBody
	@RequestMapping("/update")
	public Object update(HostInfo hostInfo) {
		hostInfoService.update(hostInfo);
		return renderSuccess();
	}
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam Integer hostInfoId) {
		boolean res = hostInfoService.delete(hostInfoId);
		if(res)	return renderSuccess();
		else return renderError("该主机不存在！");
	}
}
