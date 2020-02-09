package com.qiuzhiguo.springdemo.controller;

import com.qiuzhiguo.springdemo.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//普通用户操作自身数据相关的api
@RestController
@RequestMapping("/user/api")
public class UserController {
    @GetMapping("hello")
    public String hello(){
        return "hello,user";
    }

    @GetMapping("/get/user_info")
    public Object Info(){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        System.out.println("-----------");
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getAuthorities());
        System.out.println(userDetails.getPassword());
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails wauth= (WebAuthenticationDetails) auth.getDetails();
        LoginUser u= (LoginUser) auth.getPrincipal();
        System.out.println("当前用户id:"+u.getUserId());
        System.out.println("当前用户sessionid:"+wauth.getSessionId());
        System.out.println("当前用户名字为:"+u.getRealName());
        return null;
    }
}
