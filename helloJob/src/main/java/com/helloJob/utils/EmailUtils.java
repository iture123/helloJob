package com.helloJob.utils;

import java.io.File;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import cn.hutool.setting.dialect.Props;

public class EmailUtils {
	public static void sendByHtml(String receiver,String title,String content) throws EmailException{
		sendByHtml( receiver,null,title, content);
	}
	public static void sendByHtml(String receiver,String cc,String title,String content) throws EmailException{
		sendByHtml( receiver, cc,title, content,null);
	}
	public static void sendByHtml(String receiver,String cc,String title,String content,File attchFile) throws EmailException{
		HtmlEmail  email = new HtmlEmail();
		if(attchFile != null) {
			email.attach(attchFile);
		}
		Props props = new Props("email.properties");
		email.setHostName(props.getProperty("hostName"));
		email.setSmtpPort(Integer.parseInt(props.getProperty("smtpPort")));
		//使用邮箱账号和授权码登陆
		email.setAuthentication(props.getProperty("sender"),props.getProperty("passwd"));
		email.setSSLOnConnect(false);
		email.setCharset("utf-8");
		email.setFrom(props.getProperty("sender"));
		email.setSubject(title);
		email.setHtmlMsg(content);
		email.addTo(receiver.split(","));
		if(cc !=null && ! "".equals(cc)) {
			email.addCc(cc.split(","));
		}
		email.send();
	}

	public static void main(String[] args) {
		try {
			EmailUtils.sendByHtml("test@qq.com", "test", "<html>The apache logo - <img src=\\\"cid:\"+cid+\"\\\"></html>");
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
