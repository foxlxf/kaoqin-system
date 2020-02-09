package com.qiuzhiguo.springdemo.controller;

import com.qiuzhiguo.springdemo.entity.LoginUser;
import com.qiuzhiguo.springdemo.entity.Person;
import com.qiuzhiguo.springdemo.mapper.ClockMapper;
import com.qiuzhiguo.springdemo.mapper.UserMapper;
import com.qiuzhiguo.springdemo.util.DateUtil;
import com.qiuzhiguo.springdemo.util.PageUtil;
import com.qiuzhiguo.springdemo.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//面向客户端公开的api
@RestController
@RequestMapping("/app/api")
public class AppController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClockMapper clockMapper;

    @GetMapping("hello")
    public String hello(){
        return "hello,app";
    }

    /**
     * 打卡api
     * @param in_time 打卡的时间
     * @return
     */
    @PostMapping("/in_time")
    public Object inTime(String in_time){
        return null;
    }
    /**
     * 获取登录人的名字
     */
    @GetMapping("/getUserInfo")
    public Res GetUserInfo(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        LoginUser u= (LoginUser) auth.getPrincipal();
        return Res.ok(u.getRealName(),null);
    }

    @RequestMapping("get_user_all")
    public Object GetUserAll(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginUser u = (LoginUser) auth.getPrincipal();
        Person person=userMapper.findByPersonId(u.getUserId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("person_name",u.getRealName());
        map.put("person_sex",person.getSex());
        map.put("person_NativePlace",person.getNativePlace());
        map.put("person_birth",person.getBirth());
        map.put("person_department",person.getDepartment());
        return map;
    }

    /**
     *
     * @return
     */
    @GetMapping("/get_info")
    public PageUtil GetUserInfo(Integer limit, Integer page) {


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        String username = userDetails.getUsername();
        try {
            List<Map<String,Object>> list = clockMapper.allKaoqinByUsername(username,(page-1)*limit,limit);
            for (Map<String,Object> map:list){
                map.put("in_time", DateUtil.timestampToString((Timestamp)map.get("in_time")));
                map.put("out_time", DateUtil.timestampToString((Timestamp)map.get("out_time")));
                map.put("time_date",((java.sql.Date)map.get("time_date")).toString());
            }
            return PageUtil.ok(list,clockMapper.allKaoqinCountsByUsername(username));
        }catch (Exception e){
            e.printStackTrace();
            return PageUtil.fail("sql error");
        }
    }

}
