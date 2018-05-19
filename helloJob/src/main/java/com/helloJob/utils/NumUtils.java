package com.helloJob.utils;

public class NumUtils {
	public static boolean isLong(String input) {
		try {
			Long.parseLong(input);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	
	}
}
