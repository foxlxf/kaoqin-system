package com.qiuzhiguo.springdemo.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginUser extends User {
    private final String realName;
    private final int userId;

    public LoginUser(String username,String password,boolean enable,boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,String realName,int userId){
        super(username,password,enable,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);
        this.realName=realName;
        this.userId=userId;
    }

    public LoginUser(String username,String password,Collection<? extends GrantedAuthority> authorities,String realName,int userId){
        super(username,password,authorities);
        this.realName=realName;
        this.userId=userId;
    }

    public String getRealName(){
        return realName;
    }

    public int getUserId(){
        return userId;
    }

}
