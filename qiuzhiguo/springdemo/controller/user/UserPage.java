package com.qiuzhiguo.springdemo.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/page")
public class UserPage {
    @RequestMapping("/employWork")
    public String asd(){
        return "EmployWorkSel";
    }
}
