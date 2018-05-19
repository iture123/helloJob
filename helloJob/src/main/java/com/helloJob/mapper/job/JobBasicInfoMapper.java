package com.helloJob.mapper.job;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.vto.ComboboxVto;

public interface JobBasicInfoMapper extends BaseMapper<JobBasicInfo> {

	List<Map<String,Object>> getJobInfoList(Page<JobBasicInfo> page, Map<String, Object> map);

	List<JobBasicInfo> getPreJobList(@Param("jobId") Long jobId,@Param("jobInfo") String jobInfo);

	List<ComboboxVto> getHasJobUserList();

}
