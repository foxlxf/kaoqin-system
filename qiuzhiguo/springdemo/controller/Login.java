package com.qiuzhiguo.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Login {

    @RequestMapping("/mylogin")
    public String login() {
        //这边我们,默认是返到templates下的login.html
        return "myLogin";
    }

    @RequestMapping("/myLogout")
    public String logout() {
        //这边我们,默认是返到templates下的login.html
        return "myLogin";
    }

    @RequestMapping("/loginsuccessadmin")
    public String logins(){
        return "/admin/ManageHome";
    }

    @RequestMapping("/loginsuccessuser")
    public String loginu(){
        return "/user/EmployHome";
    }

    @RequestMapping("/loginerror")
    public String loginf(){
        return "loginf";
    }

    @RequestMapping("/user/page/EmployWorkSel")
    public String asdsa(){
        return "/user/EmployWorkSel";
    }

    @RequestMapping("/user/page/EmploySel")
    public String ddsfs(){ return "/user/EmploySel";}

    @RequestMapping("/user/page/EmployQuery")
    public String qwe(){
        return "/user/EmployQuery";
    }

    @RequestMapping("/user/page/EmployKaoQin")
    public String qwer(){
        return "/user/EmployKaoQin";
    }






}
