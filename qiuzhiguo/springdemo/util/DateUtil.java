package com.qiuzhiguo.springdemo.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author feng
 * @date 2019/12/29
 * discription:*
 */
public class DateUtil {
    private DateUtil(){}

    /**
     * yyyy-MM-dd转sql.date
     * @param str
     * @return
     */
    public static  java.sql.Date  stringToDate(String str){
        LocalDate localDate = LocalDate.parse(str);
        java.sql.Date date = java.sql.Date.valueOf(localDate);
        return date;
    }

    public static String timestampToString(Timestamp timestamp){
        String strn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        return strn;
    }



    public static void main(String[] args) {
        System.out.println(stringToDate("2019-02-10").getTime());
    }
}
