package com.helloJob.utils.job;

import java.util.HashMap;
import java.util.Map;

import com.helloJob.vto.RunningJobInfo;
import com.jcraft.jsch.ChannelExec;
/**
 * 在内存中存储正在运行的作业线程信息
 * 
 * ***/
public class RunningJobUtils {
	private static final Map<String,RunningJobInfo>  PROCESS_MAP = new HashMap<String,RunningJobInfo>();
	private RunningJobUtils() {};
	public static void kill(String jobLogId) {
		RunningJobInfo info = PROCESS_MAP.get(jobLogId);
		if(info == null) {
			throw new RuntimeException("该作业实例不存在 ！");
		}
		info.getChannelExec().disconnect();
	}
	public static void add(String jobLogId,ChannelExec channelExec) {
		RunningJobInfo info = new RunningJobInfo();
		info.setChannelExec(channelExec);
		PROCESS_MAP.put(jobLogId, info);
	}
	public static void remove(String key) {
		PROCESS_MAP.remove(key);
	}
	public static boolean isProcessExist(String jobLogId) {
		return PROCESS_MAP.get(jobLogId) == null ?  false: true;
	}
	public static RunningJobInfo get(String jobLogId) {
		return PROCESS_MAP.get(jobLogId);
	}
	public static void updateRunningJobInfo(String jobLogId,String firstLineLog,int exitVal) {
		RunningJobInfo RunningJobInfo = get(jobLogId);
		if(RunningJobInfo == null) {
			throw new RuntimeException("该作业实例不存在 ！");
		}
		RunningJobInfo.setFirstLineLog(firstLineLog);
		RunningJobInfo.setExitVal(exitVal);
		
	}
}
