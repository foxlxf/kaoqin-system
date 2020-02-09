package com.qiuzhiguo.springdemo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Page {
    @RequestMapping("/admin/page/ManageEmploye")
    public String asd(){

        return "/admin/ManageEmploye";
    }
    @RequestMapping("/admin/page/ManageSys")
    public String asdf(){
        return "/admin/ManageSys";
    }
    @RequestMapping("/admin/page/ManageWork")
    public String asdfe(){
        return "/admin/ManageWork";
    }
}
