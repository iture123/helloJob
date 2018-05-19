package com.helloJob.service.job.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.helloJob.commons.result.Tree;
import com.helloJob.mapper.job.ScheRelyJobMapper;
import com.helloJob.model.job.ScheRelyJob;
import com.helloJob.service.job.ScheRelyJobService;
@Service
public class ScheRelyJobServiceImpl  extends ServiceImpl< ScheRelyJobMapper,  ScheRelyJob>  implements ScheRelyJobService {
	@Autowired
	private ScheRelyJobMapper scheRelyJobMapper;
	@Override
	public void save(Long jobId, Long[] relyPreJob) {
		for(Long pid : relyPreJob) {
			ScheRelyJob relyJob = new ScheRelyJob();
			relyJob.setJobId(jobId);
			relyJob.setPid(pid);
			scheRelyJobMapper.insert(relyJob);
		}
	}
	@Override
	public void deleteParentJobs(Long jobId) {
		Wrapper<ScheRelyJob> wrapper = new EntityWrapper<>();
		wrapper.where("job_id = {0}", jobId);
		scheRelyJobMapper.delete(wrapper );
	}
	@Override
	public List<ScheRelyJob> getTriggerJobList(Long jobId) {
		Wrapper<ScheRelyJob> wrapper = new EntityWrapper<>();
		wrapper.where("pid = {0}", jobId);
		return scheRelyJobMapper.selectList(wrapper);
	}
	@Override
	public List<Long> getReferJobIdsOfOneJob(Long jobId) {
		return scheRelyJobMapper.getReferJobIdsOfOneJob(jobId);
	}
	@Override
	public Set<Long> getReferJobIds(Long jobId) {
		Set<Long> referJobIds = Sets.newHashSet();
		addReferJobIds(referJobIds,getReferJobIdsOfOneJob(jobId));
		referJobIds.add(jobId);
		return referJobIds;
	}
	private void addReferJobIds(	Set<Long> referJobIds ,List <Long> tmpIds) {
		for(Long id : tmpIds) {
			if(! referJobIds.contains(id)) {
				referJobIds.add(id);
				addReferJobIds(referJobIds,getReferJobIdsOfOneJob( id));
			}
		}
	}
	@Override
	public List<Tree> getTreeList(Long jobId) {
		Set<Long> referJobIds = getReferJobIds(jobId);
		List<Tree> treeList = scheRelyJobMapper.getTreeList(referJobIds );
		return treeList;
	}
	@Override
	public List<Long> getTriggerJobs(Long jobId) {
		return scheRelyJobMapper.getTriggerJobs(jobId);
	}
	private void addTriggerJobs(Set<Long> allTriggerJobs ,List <Long> tmpIds) {
		for(Long id : tmpIds) {
			if(! allTriggerJobs.contains(id)) {
				allTriggerJobs.add(id);
				addTriggerJobs(allTriggerJobs,getTriggerJobs( id));
			}
		}
	}
	@Override
	public Set<Long> getAllTriggerJobs(Long jobId) {
		Set<Long> allTriggerJobs = Sets.newHashSet();
		addTriggerJobs(allTriggerJobs, getTriggerJobs(jobId));
		return allTriggerJobs;
	}
}
