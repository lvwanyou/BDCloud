package com.xl.cloud.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

public class DateUtil {
	
	public static String getBetween(String fromDate, String toDate){
		
//		fromDate = "2013-04-16 08:29:12";  
//		toDate = "2013-04-20 09:44:29";  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        String hourStr = "00";
        String minStr = "00";
        String secondStr = "00";
        try {
        	if(fromDate!=null && !fromDate.equals("") && toDate!=null && !toDate.equals("")){
        		//前的时间   
                Date fd = df.parse(fromDate);  
                //后的时间  
                Date td = df.parse(toDate);  
                //两时间差,精确到毫秒   
                long diff = td.getTime() - fd.getTime();  
                long hour = diff / 3600000;               //以小时为单位取整  
                long min = diff % 3600000 / 60000;       //以分钟为单位取整  
                long seconds = diff % 3600000 % 60000 / 1000;   //以秒为单位取整  
                //时分秒
                hourStr = String.valueOf(hour);
                minStr = String.valueOf(min);
                secondStr = String.valueOf(seconds);
                if(hour<10){
                	hourStr = "0"+hourStr;
                }
                if(min<10){
                	minStr = "0"+minStr;
                }
                if(seconds<10){
                	secondStr = "0"+secondStr;
                }
        	}
        } catch (ParseException e) {  
//            e.printStackTrace();
        	System.out.println(e.getMessage());
        }
//        System.out.println("两时间差---> " +hourStr+"小时"+minStr+"分"+secondStr+"秒");
        return hourStr+":"+minStr+":"+secondStr;
	}
	
	public static void main(String[] args) {
		getBetween("", "");
	}
	
	@Test
	public void test1(){
		System.out.println(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
	}
}
