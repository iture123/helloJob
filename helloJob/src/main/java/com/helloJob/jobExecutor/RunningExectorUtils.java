package com.helloJob.jobExecutor;

import java.util.HashMap;
import java.util.Map;

public class RunningExectorUtils {
	private static final Map<String,AbstractExecutor>  Exector_MAP = new HashMap<>();
	private static final Map<String,String> FIRST_lINE_MAP = new HashMap<String, String>();
	
	public static void add(String jobLogId,AbstractExecutor executor) {
		Exector_MAP.put(jobLogId, executor);
		FIRST_lINE_MAP.put(jobLogId, "");
	}
	public static final void updateFirstLine(String jobLogId,String firstLine) {
		FIRST_lINE_MAP.put(jobLogId, firstLine);
	}
	public static final String getFirstLine(String jobLogId) {
		return FIRST_lINE_MAP.get(jobLogId);
	}
	
	public static void kill(String jobLogId) {
		if(! Exector_MAP.containsKey(jobLogId)) {
			throw new RuntimeException("该作业实例不存在 ！");
		}
		Exector_MAP.get(jobLogId).interruptExec();
	}
	public static void remove(String jobLogId) {
		Exector_MAP.remove(jobLogId);
		FIRST_lINE_MAP.remove(jobLogId);
	}
}
