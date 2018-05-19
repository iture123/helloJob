package com.helloJob.vto;
/**
 * 作业执行结果
 * **/
public class JobExecResult {
	private boolean isSuccess;
	private String log;
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
}
