package com.helloJob.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtils {
	 public static String extractData(String content, String p) {
			Pattern pattern = Pattern.compile(p, Pattern.DOTALL);
			Matcher m = pattern.matcher(content);
			
			if (m.find()) {
				return m.group(1);
			}
			
			return "";
		}
 public static void main( String[] args )
 {
 	System.out.println(extractData("连通率:53355","连通率:(\\d+)"));
 	System.out.println( RegUtils.extractData("Submitted application application_1523720062044_0006<br>", "Submitted application (application_.*)<br>"));
System.out.println(extractData("刷新时间：2017-01-20 12:16:12 <p>", "刷新时间：(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})"));
System.out.println(RegUtils.extractData("Starting Job = job_1524281581858_0016, Tracking URL ", "Starting Job = job_(.*), Tracking URL "));
 }

}
