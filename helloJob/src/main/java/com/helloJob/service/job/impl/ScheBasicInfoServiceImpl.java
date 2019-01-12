package com.helloJob.service.job.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.helloJob.constant.ScheTypeConst;
import com.helloJob.executor.CommonJobExecEnginer;
import com.helloJob.executor.RunningExectorUtils;
import com.helloJob.mapper.job.ScheBasicInfoMapper;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.model.job.ScheBasicInfo;
import com.helloJob.service.job.JobBasicInfoService;
import com.helloJob.service.job.JobLogService;
import com.helloJob.service.job.ScheBasicInfoService;
import com.helloJob.service.job.ScheRelyJobService;
import com.helloJob.utils.job.JobThreadPool;
import com.helloJob.utils.job.QuartzManager;
import com.helloJob.vto.RunningJobInfo;
@Service
public class ScheBasicInfoServiceImpl  extends ServiceImpl< ScheBasicInfoMapper,  ScheBasicInfo> implements ScheBasicInfoService{
	@Autowired
	private ScheBasicInfoMapper scheBasicInfoMapper;
	@Autowired
	private JobBasicInfoService JobBasicInfoService;
	@Autowired
	private ScheRelyJobService scheRelyJobService;
	@Autowired
	private JobLogService jobLogService;
	@Override
	public void save(ScheBasicInfo scheInfo) {
		scheBasicInfoMapper.insert(scheInfo);
	}
	@Override
	public void mountScheByTime(Long jobId, String cron) {
		QuartzManager.addJob(jobId+"", cron);
	}
	@Override
	public void runOnce(long jobId, int dt, String isSelfRely) {
		JobBasicInfo job = JobBasicInfoService.get( jobId);
		ScheBasicInfo scheInfo =new ScheBasicInfo();
		scheInfo.setIsSelfRely(isSelfRely);
		scheInfo.setTryCount(0);
		JobThreadPool.getInstance().execute(new CommonJobExecEnginer(job,scheInfo, dt));
		//CommonJobExecEnginer.execute(job,scheInfo, dt);
	}
	@Override
	public ScheBasicInfo getScheInfo(Long jobId) {
	  ScheBasicInfo scheInfo = scheBasicInfoMapper.selectById(jobId);
	  return scheInfo;
	}
	@Override
	public void stopTimeSchedule(Long jobId) {
		QuartzManager.removeJob(jobId+"");
		deleteByJobId(jobId);
	}
	@Override	
	public void stopRelyJobSchedule(Long jobId) {
		deleteByJobId(jobId);
		scheRelyJobService.deleteParentJobs(jobId);
	}
	private void deleteByJobId(Long jobId) {
		Wrapper<ScheBasicInfo> wrapper = new EntityWrapper<>();
		wrapper.where("job_id = {0}", jobId);
		scheBasicInfoMapper.delete(wrapper );
	}
	@Override
	public List<ScheBasicInfo> getScheByTime() {
		ScheBasicInfo scheBasicInfo = new ScheBasicInfo();
		scheBasicInfo.setScheType(ScheTypeConst.TIME_SCHE);
		Wrapper<ScheBasicInfo> wrapper = new EntityWrapper<>(scheBasicInfo);
		return scheBasicInfoMapper.selectList(wrapper);
	}
	@Override
	public void killJobs(Set<Long> jobIds, Integer dt,String firstLineLog) {
		List<String> jobLogIds = jobLogService.getRunningJobLogIds(jobIds, dt);
		for(String jobLogId : jobLogIds) {
			RunningExectorUtils.kill(jobLogId);
			/*
			 * RunningJobInfo runningJobInfo = RunningJobUtils.get(jobLogId);
			 * if(runningJobInfo !=null) { runningJobInfo.setExitVal(-1);
			 * runningJobInfo.setFirstLineLog(firstLineLog); RunningJobUtils.kill(jobLogId);
			 * }
			 */
		}
	}
}
