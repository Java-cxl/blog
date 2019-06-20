package cn.cxl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateChangeUtil {

    //日期转换为String类型
    public String getStringByDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String result=sdf.format(date);
        return result;
    }

    //String转换为日期类型
    public Date getDateByString(String date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date result= null;
        try {
            result = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
