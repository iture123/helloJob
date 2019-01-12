package com.helloJob.executor.impl;

import java.io.IOException;

import com.helloJob.executor.AbstractExecutor;
import com.helloJob.executor.impl.common.StreamGobbler;
import com.helloJob.model.job.JobBasicInfo;

/**
 * 执行本地命令，windows执行cmd，linux执行bash
 * 
 * @author iture
 *
 */
public class LocalExecutor extends AbstractExecutor {

	public LocalExecutor(JobBasicInfo job, Integer dt) {
		super(job, dt);
	}

	Process proc = null;


	/**
	 * 判断本地主机是否属于windows系统
	 */
	private boolean isRunOnWindows() {
		String osName = System.getProperties().getProperty("os.name");
		if (osName.toLowerCase().startsWith("windows")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean execCommand(String command) {
		Runtime rt = Runtime.getRuntime();
		try {
			if (isRunOnWindows()) {
				proc = rt.exec(new String[] { "cmd.exe", "/c", command });
			} else {
				proc = rt.exec(new String[] { "/bin/bash", "-c", command });
			}
			// 接收控制台打印的日志
			Thread errorGobbler = new Thread(new StreamGobbler(proc.getErrorStream(), super.logBuffer));
			Thread outputGobbler = new Thread(new StreamGobbler(proc.getInputStream(), super.logBuffer));
			errorGobbler.start();
			outputGobbler.start();
			errorGobbler.join();
			outputGobbler.join();
			// 等待执行结束退出
			int exitVal = proc.waitFor();
			return exitVal == 0;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void interruptExec() {
		proc.destroyForcibly();
	}


}
