package com.helloJob.utils;

public class ThreadUtils {
	public static void sleeep(long ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
