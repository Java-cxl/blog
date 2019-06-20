package cn.cxl.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RandomUtil {
	
	//根据系统时间+四位随机数生成字符串
	public static String getName(){
		Calendar calendar=Calendar.getInstance();
		String year=String.valueOf(calendar.get(Calendar.YEAR));
		String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
		String day=String.valueOf(calendar.get(Calendar.DATE));
		String hour=String.valueOf(calendar.get(Calendar.HOUR));
		String minute=String.valueOf(calendar.get(Calendar.MINUTE));
		String second=String.valueOf(calendar.get(Calendar.SECOND));
		String ran=String.valueOf((int)Math.random()*8998+1000+1);
		String str=year+month+day+hour+minute+second+ran;
		return str;
	}
}
