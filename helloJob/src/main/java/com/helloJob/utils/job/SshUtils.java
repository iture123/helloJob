package com.helloJob.utils.job;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.helloJob.model.job.HostInfo;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.model.job.JobLog;
import com.helloJob.service.job.HostInfoService;
import com.helloJob.service.job.JobLogService;
import com.helloJob.utils.ApplicationContextUtil;
import com.helloJob.vto.JobExecResult;
import com.helloJob.vto.RunningJobInfo;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import cn.hutool.core.util.StrUtil;

public class SshUtils {
	protected static Logger logger = LogManager.getLogger(SshUtils.class);

	public static JobExecResult execute(JobBasicInfo job, Integer dt) {
		JobLogService jobLogService = ApplicationContextUtil.getContext().getBean(JobLogService.class);
		HostInfoService hostInfoService = ApplicationContextUtil.getContext().getBean(HostInfoService.class);
		JobLog jobLog = jobLogService.addRunningLog(job.getId(), dt, job);
		JobExecResult jobExecResult = new JobExecResult();
		try {
			HostInfo hostInfo = hostInfoService.get(job.getHostId());
			String host = hostInfo.getHost();// 服务器地址
			String userName = hostInfo.getUsername();// 用户名
			String password = hostInfo.getPasswd();// 密码
			int port = hostInfo.getPort();// 端口号
			JSch jsch = new JSch(); // 创建JSch对象
			// String cmd = "hive -e \"select id,count(1) from stu group by id\"";// 要运行的命令
			String cmd = StrUtil.replaceChars(job.getCommand(), "\r\n", "");
			logger.info("执行命令:" + cmd);
			Session session = jsch.getSession(userName, host, port);
			// 根据用户名，主机ip，端口获取一个Session对象
			session.setPassword(password); // 设置密码
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config); // 为Session对象设置properties
			int timeout = 2 * 60 * 60 * 1000;
			session.setTimeout(timeout); // 设置timeout时间
			session.connect(); // 通过Session建立链接
			ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
			channelExec.setCommand(cmd);
			channelExec.setInputStream(null);
			channelExec.setErrStream(null);
			channelExec.connect();
			RunningJobUtils.add(jobLog.getId(), channelExec);
			StringBuffer sb = new StringBuffer();
			// any error message?
			Thread errorGobbler = new Thread(new StreamGobbler(channelExec.getErrStream(), "ERROR", sb));
			// any output?
			Thread outputGobbler = new Thread(new StreamGobbler(channelExec.getInputStream(), "OUTPUT", sb));
			// kick them off
			errorGobbler.start();
			outputGobbler.start();
			Thread monitorLogChange = new Thread(new MonitorLogChange(jobLog, sb));
			monitorLogChange.start();
			errorGobbler.join();
			outputGobbler.join();
			channelExec.disconnect();
			int exitStatus = channelExec.getExitStatus() == 0 ? 0 : -1;
			RunningJobInfo runningJobInfo = RunningJobUtils.get(jobLog.getId());
			if (exitStatus == 0) {
				// kettle作业处理
				if (cmd.contains("Kitchen") || cmd.contains("Pan")) {
					if (sb.toString().contains("Finished with errors") || sb.toString().contains("ERROR: Kitchen")
							|| sb.toString().contains("org.pentaho.di.core.exception")) {
						exitStatus = -1;
						jobLogService.updateError(jobLog, runningJobInfo.getFirstLineLog() + sb.toString());
					}
				} else {
					jobLogService.updateSuccess(jobLog);
				}
			} else {
				monitorLogChange.interrupt();
				logger.warn("作业执行失败");
				jobLogService.updateError(jobLog, runningJobInfo.getFirstLineLog() + sb.toString());
			}
			runningJobInfo.setExitVal(exitStatus);
			if (null != session) {
				session.disconnect();
			}

			jobExecResult.setSuccess(runningJobInfo.getExitVal() == 0);
			jobExecResult.setLog(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			jobExecResult.setSuccess(false);
			jobExecResult.setLog("异常：" + e.getMessage());
			jobLogService.updateError(jobLog, e.getMessage());
		}
		return jobExecResult;
	}

	public static boolean isHostReachable(String host) {
		try {
			return InetAddress.getByName(host).isReachable(2);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
