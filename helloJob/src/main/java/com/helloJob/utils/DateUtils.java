package com.helloJob.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	public static String getFormatDate(Date date , String formatStr){
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String formatDate = sdf.format(date);
		return formatDate;
	}
	public static String getFormatDate(String formatStr){
		Date date = new Date();
		return getFormatDate(date , formatStr);
	}
	public static int getYyyyMMdd(){
		String date = getFormatDate("yyyyMMdd");
		return Integer.parseInt(date);
	}
	public static int getYear(){
		String date = getFormatDate("yyyy");
		return Integer.parseInt(date);
	}
/*	public static int getMonth(){
		String date = getFormatDate("MM");
		return Integer.parseInt(date);
	}
	public static int getDay(){
		String date = getFormatDate("dd");
		return Integer.parseInt(date);
	}*/
	/**
	 * 获取昨天日期，返回格式yyyyMMdd
	 * **/
	public static int getYesterday(){
		Date yesterday = addDay(new Date() , -1);
		return Integer.parseInt(getFormatDate(yesterday, "yyyyMMdd"));
	}
	public static String getYesterday(String format){
		Date yesterday = addDay(new Date() , -1);
		return getFormatDate(yesterday, format);
	}
	/**对日期进行加减
	 * */
	public static Date addDay(Date date,int amount){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, amount);
		return c.getTime();
	}
	/**对日期进行加减
	 * */
	public static Date addMonth(Date date,int amount){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, amount);
		return c.getTime();
	}
	public static String getTTime(Date date){
		return getFormatDate(date,"yyyyMMdd");
	}
	public static String getHour(Date date){
		return getFormatDate(date,"HH");
	}
	public static String getMinute(Date date){
		return getFormatDate(date,"mm");
	}
	public static Date getDate(int flag){
		Date date=new Date();//取时间
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE,flag);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
         return date;		
	}
	public static int getYear(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return Integer.parseInt(formatter.format(date));
	}
	public static int getMonth(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		return Integer.parseInt(formatter.format(date));
	}  
	public static int getDay(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("dd");
		return Integer.parseInt(formatter.format(date));
	}
	/**获取上个月第一天
	 * */
	public static Date getLastMonthFirstDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	/**获取上个月最后一天
	 * */
	public static Date getLastMonthEndDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	/**获取指定月份最后一天
	 * */
	public static String getEndDay(Date assignDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(assignDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return getFormatDate(calendar.getTime(), "dd");
	}
	public static void main(String[] args){
		Date queryDate = DateUtils.parse("201612", "yyyyMM");
		String endDay = DateUtils.getEndDay(queryDate);
		System.out.println(endDay);
	}
	public static Date parse(String dateStr , String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**判断该日期是否属于昨天
	 * **/
	public static boolean isYesterDay(Date queryDate){
		String queryDateStr = getFormatDate(queryDate, "yyyyMMdd");
		String yesterDay = getFormatDate(addDay(new Date(), -1), "yyyyMMdd");
		if(queryDateStr.equals(yesterDay)){
			return true;
		}else{
			return false;
		}
	}
	public static String getCreateTime() {
		return getFormatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
	}
	public static String getFormatTime(Date date){
		return getFormatDate(date,"yyyy-MM-dd HH:mm:ss");
	}
	public static String getFormatTime(long ms){
		return getFormatDate(new Date(ms),"yyyy-MM-dd HH:mm:ss");
	}
}
