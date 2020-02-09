package com.qiuzhiguo.springdemo.service;

import com.qiuzhiguo.springdemo.entity.LoginUser;
import com.qiuzhiguo.springdemo.entity.Person;
import com.qiuzhiguo.springdemo.entity.Users;
import com.qiuzhiguo.springdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        System.out.println("username="+username);

        //从数据库尝试读取该用户
        Users user = userMapper.findByUsername(username);

        System.out.println("user密码为:"+user.getPassword());

        System.out.println("user权限为:"+user.getRoles());

        //用户不存在,抛出异常
        if(user==null){
            throw new UsernameNotFoundException("用户不存在");
        }

        Person person = userMapper.findByPersonId(user.getId());

        System.out.println(user.getRoles());



        return new LoginUser(user.getUsername(),
                user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()),person.getName(),user.getId());

    }

    private List<GrantedAuthority> generateAuthorities(String roles){

        System.out.println("user权限为:"+roles);

        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] roleArray = roles.split(";");
        System.out.println(roleArray);
        if(roles!=null&&!"".equals(roles)){
            for(String role:roleArray){
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }

}
