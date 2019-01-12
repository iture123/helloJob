package com.helloJob.executor.impl;

import java.io.IOException;
import java.util.Properties;

import com.helloJob.executor.AbstractExecutor;
import com.helloJob.executor.impl.common.StreamGobbler;
import com.helloJob.model.job.HostInfo;
import com.helloJob.model.job.JobBasicInfo;
import com.helloJob.model.job.ScheBasicInfo;
import com.helloJob.service.job.HostInfoService;
import com.helloJob.utils.ApplicationContextUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import cn.hutool.core.util.StrUtil;

public class SshExector extends AbstractExecutor {

	public SshExector(JobBasicInfo job, Integer dt) {
		super(job, dt);
	}

	private ChannelExec channelExec;

	@Override
	protected boolean execCommand(String command) {
			HostInfoService hostInfoService = ApplicationContextUtil.getContext().getBean(HostInfoService.class);
			HostInfo hostInfo = hostInfoService.get(super.job.getHostId());
			String host = hostInfo.getHost();// 服务器地址
			String userName = hostInfo.getUsername();// 用户名
			String password = hostInfo.getPasswd();// 密码
			int port = hostInfo.getPort();// 端口号
			JSch jsch = new JSch(); // 创建JSch对象
			// String cmd = "hive -e \"select id,count(1) from stu group by id\"";// 要运行的命令
			String cmd = StrUtil.replaceChars(job.getCommand(), "\r\n", "");
			Session session;
			try {
				session = jsch.getSession(userName, host, port);
				// 根据用户名，主机ip，端口获取一个Session对象
				session.setPassword(password); // 设置密码
				Properties config = new Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config); // 为Session对象设置properties
				int timeout = 2 * 60 * 60 * 1000;
				session.setTimeout(timeout); // 设置timeout时间
				session.connect(); // 通过Session建立链接
				channelExec = (ChannelExec) session.openChannel("exec");
				channelExec.setCommand(cmd);
				channelExec.setInputStream(null);
				channelExec.setErrStream(null);
				channelExec.connect();
				// any error message?
				Thread errorGobbler = new Thread(new StreamGobbler(channelExec.getErrStream(), super.logBuffer));
				// any output?
				Thread outputGobbler = new Thread(new StreamGobbler(channelExec.getInputStream(), super.logBuffer));
				// kick them off
				errorGobbler.start();
				outputGobbler.start();
				errorGobbler.join();
				outputGobbler.join();
				channelExec.disconnect();
				return channelExec.getExitStatus() == 0 ;
			} catch (JSchException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
			
	}

	@Override
	public void interruptExec() {
		channelExec.disconnect();
	}

}
