package com.helloJob.jobExecutor;

import java.util.HashMap;
import java.util.Map;

public class RunningExectorUtils {
	private static final Map<String,AbstractExecutor>  Exector_MAP = new HashMap<>();
	public static void add(String jobLogId,AbstractExecutor executor) {
		Exector_MAP.put(jobLogId, executor);
	}
	public static void kill(String jobLogId) {
		if(! Exector_MAP.containsKey(jobLogId)) {
			throw new RuntimeException("该作业实例不存在 ！");
		}
		Exector_MAP.get(jobLogId).interruptExec();
		Exector_MAP.remove(jobLogId);
	}
	public static void remove(String jobLogId) {
		Exector_MAP.remove(jobLogId);
	}
}
