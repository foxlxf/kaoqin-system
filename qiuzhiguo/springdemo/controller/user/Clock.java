package com.qiuzhiguo.springdemo.controller.user;

import com.qiuzhiguo.springdemo.entity.LoginUser;
import com.qiuzhiguo.springdemo.entity.PersistentLogins;
import com.qiuzhiguo.springdemo.entity.Worktime;
import com.qiuzhiguo.springdemo.mapper.ClockMapper;
import com.qiuzhiguo.springdemo.util.DateUtil;
import com.qiuzhiguo.springdemo.util.MD5Util;
import com.qiuzhiguo.springdemo.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.jar.JarOutputStream;

/**
 * 员工打卡
 */
@RestController
@RequestMapping("/user/api")
public class Clock {

    @Autowired
    private ClockMapper clockMapper;


    @RequestMapping("change_password")
    public Res ChangePassword(String password){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        LoginUser u= (LoginUser) auth.getPrincipal();
        String pwd= MD5Util.encode(password);
        clockMapper.updateUserPasswd(pwd,u.getUserId());
        return Res.ok();
    }

    /**
     * 上班日打卡
     * 早晨打卡
     * @param in_time 打卡的时间戳
     * @return
     */
    @RequestMapping("clock/in_time")
    public Object morning_clock(long in_time,String date) throws ParseException {



        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        LoginUser u= (LoginUser) auth.getPrincipal();


        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        System.out.println(date);
        Worktime wt=clockMapper.findByTimeDate(date);
        System.out.println(wt==null);
        System.out.println("===____");
        System.out.println(wt.getTimeDate());
        System.out.println("===____");
        Date d1=new Date(in_time);
        Date d2=new Date(wt.getInTime().getTime());
        Date date1 = sd.parse(sd.format(d1));  //打卡时间
        Date date2 = sd.parse(sd.format(d2));  //工作表中的时间
        Calendar cal = Calendar.getInstance();
        cal.setTime(date2);
        cal.add(Calendar.HOUR, 1);// 24小时制
        Date date3 = cal.getTime();   //工作表的时间加一个小时
        cal = null;

        int compareTo1 = date1.compareTo(date2);
        int compareTo2 = date1.compareTo(date3);

        if(compareTo1==-1&&compareTo2==-1){
            PersistentLogins persistentLogins = clockMapper.findByOneDay(u.getUserId(),wt.getTimeDate(),0);
            if(persistentLogins!=null){
                return Res.ok("请勿重复打卡",null);
            }else {
                PersistentLogins logins = new PersistentLogins();
                logins.setPersonId(u.getUserId());
                Timestamp ts = new Timestamp(sd.parse(sd.format(d1)).getTime());
                logins.setInTime(ts);
                logins.setNowTime(wt.getTimeDate());
                logins.setStatus(0);
                clockMapper.insertInfo(logins);
                return Res.ok("打卡成功！",null);
            }
        }else if(compareTo1==1&&compareTo2==-1) {
            PersistentLogins persistentLogins = clockMapper.findByOneDay(u.getUserId(),wt.getTimeDate(),1);
            if(persistentLogins!=null){
                return Res.ok("请勿重复打卡",null);
            }else {
                PersistentLogins logins = new PersistentLogins();
                logins.setPersonId(u.getUserId());
                Timestamp ts = new Timestamp(sd.parse(sd.format(d1)).getTime());
                logins.setInTime(ts);
                logins.setNowTime(wt.getTimeDate());
                logins.setStatus(1);
                clockMapper.insertInfo(logins);
                return Res.ok("打卡成功,打卡状态:迟到",null);
            }
        }else if(compareTo1==1&&compareTo2==1){
                return Res.ok("超出打卡时间,无法打卡!",null);
            }

        return null;
    }

    /**
     *  下午打卡
     * @param out_time 下午打卡的时间
     * @return
     */
    @RequestMapping("clock/out_time")
    public Object afternoon_clock(long out_time,String date) throws ParseException {

        System.out.println(out_time);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        LoginUser u= (LoginUser) auth.getPrincipal();

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Worktime wt=clockMapper.findByTimeDate(date);

        Date d1 = new Date(out_time);
        Date d2=new Date(wt.getOutTime().getTime());

        Date date1 = sd.parse(sd.format(d1));  //打卡时间
        Date date2 = sd.parse(sd.format(d2));  //工作表中的时间
        Calendar cal = Calendar.getInstance();
        cal.setTime(date2);
        cal.add(Calendar.HOUR, 1);// 24小时制
        Date date3 = cal.getTime();   //工作表的时间加一个小时
        cal = null;

        int compareTo1 = date1.compareTo(date2);
        int compareTo2 = date1.compareTo(date3);

        if(compareTo1==-1){
           return Res.ok("未到打卡时间,请勿打卡!",null);
        }else if(compareTo1==1&compareTo2==-1){

            PersistentLogins pl = clockMapper.findByOneDayNoStatus(u.getUserId(),wt.getTimeDate());
            System.out.println("=======+++++");
            System.out.println(pl);
            if(pl==null){
                PersistentLogins logins = new PersistentLogins();
                logins.setPersonId(u.getUserId());
                Timestamp ts = new Timestamp(sd.parse(sd.format(d1)).getTime());
                logins.setOutTime(ts);
                logins.setNowTime(wt.getTimeDate());
                logins.setStatus(1);
                clockMapper.insertInfo(logins);
                return Res.ok("打卡成功!",null);
            }else if(pl.getOutTime()==null){
                if(pl.getStatus()==0){
                    PersistentLogins logins = new PersistentLogins();
                    logins.setPersonId(u.getUserId());
                    Timestamp ts = new Timestamp(sd.parse(sd.format(d1)).getTime());
                    logins.setOutTime(ts);
                    logins.setNowTime(wt.getTimeDate());
                    logins.setStatus(1);
                    clockMapper.insertInfo(logins);
                    return Res.ok("打卡成功!",null);
                }else if(pl.getStatus()==3){
                    PersistentLogins logins = new PersistentLogins();
                    logins.setPersonId(u.getUserId());
                    Timestamp ts = new Timestamp(sd.parse(sd.format(d1)).getTime());
                    logins.setOutTime(ts);
                    logins.setNowTime(wt.getTimeDate());
                    logins.setStatus(3);
                    clockMapper.insertInfo(logins);
                    return Res.ok("打卡成功!",null);
                }
            }else if(pl.getOutTime()!=null){
                return Res.ok("请勿重复打卡!",null);
            }
        }else if(compareTo2==1){
            return Res.ok("超出打卡时间,无法打卡",null);
        }


        return null;
    }

    public static String getTime(Date currentTime,Date firstTime){
        long diff = currentTime.getTime() - firstTime.getTime();//这样得到的差值是微秒级别
        Calendar currentTimes =dataToCalendar(currentTime);//当前系统时间转Calendar类型
        Calendar firstTimes =dataToCalendar(firstTime);//查询的数据时间转Calendar类型
        int year = currentTimes.get(Calendar.YEAR) - firstTimes.get(Calendar.YEAR);//获取年
        int month = currentTimes.get(Calendar.MONTH) - firstTimes.get(Calendar.MONTH);
        int day = currentTimes.get(Calendar.DAY_OF_MONTH) - firstTimes.get(Calendar.DAY_OF_MONTH);
        if (day < 0){
            month-=1;
            currentTimes.add(Calendar.MONTH, -1);
            day = day + currentTimes.getActualMaximum(Calendar.DAY_OF_MONTH);//获取日
        }
        if (month<0){
            month = (month + 12) % 12;//获取月
            year--;
        }
        long days = diff / (1000 * 60 * 60 * 24);
        long hours=(diff-days*(1000*60*60*24))/(1000* 60*60);//获取时
        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);//获取分钟
        long s=(diff/1000-days*24*60*60-hours*60*60-minutes*60);//获取秒
        String CountTime=""+"year"+"年"+month+"月"+day+"天"+hours+"小时"+minutes+"分"+s+"秒";
        return CountTime;
    }

    // Date类型转Calendar类型
    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


}
