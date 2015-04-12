package com.zx.utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期工具类
 * @author zx
 *
 */
public class DateTransferUtil {
	private DateTransferUtil(){ 
	}
	public static String long2String(long d){
		Date date = new Date(d);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(date);
	}
	public static long string2Long(String strDate) throws ParseException{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = df.parse(strDate);
		return d.getTime();
	}
}

