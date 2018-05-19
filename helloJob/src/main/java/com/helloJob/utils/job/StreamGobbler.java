package com.helloJob.utils.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringEscapeUtils;

public class StreamGobbler implements Runnable  {
	InputStream is;
	String type;
	StringBuffer sb;
	StreamGobbler(InputStream is, String type,StringBuffer sb ) {
		this.is = is;
		this.type = type;
		this.sb = sb;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				if(! line.trim().equals("")  || ! sb.toString().equals("") ) {
					sb.append( StringEscapeUtils.escapeHtml(line)+"<br>");
				}
			   br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
