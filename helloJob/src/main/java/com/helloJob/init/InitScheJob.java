package com.helloJob.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.alibaba.fastjson.JSONObject;
import com.helloJob.model.job.ScheBasicInfo;
import com.helloJob.service.job.JobLogService;
import com.helloJob.service.job.ScheBasicInfoService;
import com.helloJob.utils.ApplicationContextUtil;
import com.helloJob.utils.job.QuartzManager;

/**
 * 初始化调度作业
 * **/
public class InitScheJob implements ApplicationListener<ContextRefreshedEvent> {
	public static final Logger log = LoggerFactory.getLogger(InitScheJob.class.getName());
	@Autowired
	private ScheBasicInfoService scheBasicInfoService;
	@Autowired
	private JobLogService jobLogService;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
		if(event.getApplicationContext().getParent() == null){
			log.info("spring 初始化执行");
			ApplicationContextUtil.setContext(event.getApplicationContext());
			List<ScheBasicInfo> list = scheBasicInfoService.getScheByTime();
			QuartzManager.Init(event.getApplicationContext());
			if(list.size()>0){
				for(ScheBasicInfo scheInfo : list){
					log.info(JSONObject.toJSONString(scheInfo));
					QuartzManager.addJob(scheInfo.getJobId(), scheInfo.getCron());
				}
			}
			//将重启前，状态为正在运行的记录更新为失败
			jobLogService.updateRunningToError();
		}
	}

}
