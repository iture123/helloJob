package com.helloJob.controller.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helloJob.commons.base.BaseController;
import com.helloJob.commons.result.PageInfo;
import com.helloJob.model.job.HostInfo;
import com.helloJob.service.job.HostInfoService;
import com.helloJob.utils.DateUtils;

@Controller
@RequestMapping("/host")
public class HostInfoController extends BaseController {
	@Autowired
	private HostInfoService hostInfoService;

	@GetMapping("/hostInfo")
	public String hostInfo() {
		return "job/hostInfo";
	}
	@ResponseBody
	@RequestMapping("/get")
	public Object get(@RequestParam Integer id) {
		HostInfo hostInfo = hostInfoService.get(id);
		if(hostInfo == null) {
			return renderError("请刷新重试！");
		}
		return renderSuccess(hostInfo);
	}
	@ResponseBody
	@RequestMapping("/add")
	public Object add(HostInfo hostInfo) {
		hostInfo.setCreater(getUserId());
		hostInfo.setCreateTime(DateUtils.getCreateTime());
		hostInfoService.add(hostInfo);
		return renderSuccess();
	}

	@ResponseBody
	@RequestMapping("/update")
	public Object update(HostInfo hostInfo) {
		if(hostInfo.getId()==1) {
			return renderError("该记录不允许修改！");
		}
		hostInfoService.update(hostInfo);
		return renderSuccess();
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam Integer hostInfoId) {
		boolean res = hostInfoService.delete(hostInfoId);
		if (res)
			return renderSuccess();
		else
			return renderError("该主机不存在！");
	}

	@ResponseBody
	@RequestMapping("/datagrid")
	public Object datagrid(Integer page, Integer rows,
			@RequestParam(value = "sort", defaultValue = "create_time") String sort,
			@RequestParam(value = "order", defaultValue = "DESC") String order) {
		PageInfo pageInfo = new PageInfo(page, rows, sort, order);
		hostInfoService.datagrid(pageInfo);
		return pageInfo;
	}
	@ResponseBody
	@RequestMapping("/getAllList")
	public Object getAllList() {
		 List<HostInfo> list = hostInfoService.getAllList();
		 return list;
	}
}
