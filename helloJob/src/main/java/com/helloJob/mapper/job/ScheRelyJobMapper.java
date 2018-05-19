package com.helloJob.mapper.job;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.helloJob.commons.result.Tree;
import com.helloJob.model.job.ScheRelyJob;

public interface ScheRelyJobMapper   extends BaseMapper<ScheRelyJob>{

	List<Long> getReferJobIdsOfOneJob(@Param("jobId") Long jobId);

	List<Tree> getTreeList(@Param("referJobIds") Set<Long> referJobIds);

	List<Long> getTriggerJobs(@Param("jobId") Long jobId);

}
