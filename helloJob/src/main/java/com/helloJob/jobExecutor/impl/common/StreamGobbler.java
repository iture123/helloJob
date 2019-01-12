package com.helloJob.jobExecutor.impl.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler implements Runnable {
	InputStream is;
	StringBuffer sb;
	public StreamGobbler(InputStream is, StringBuffer sb) {
		this.is = is;
		this.sb = sb;
	}

	@Override

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				sb.append(line + "<br>");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
