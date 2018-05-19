package com.helloJob.mapper.job;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.helloJob.model.job.ScheBasicInfo;

public interface ScheBasicInfoMapper  extends BaseMapper<ScheBasicInfo>{

	void deleteByJobId(Long jobId);

}
