package com.qiuzhiguo.springdemo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        System.out.println(userDetails.getAuthorities());
        if(userDetails.getAuthorities().toString().equals("[ROLE_ADMIN, ROLE_USER]")){
            System.out.println("123123123");
            httpServletResponse.sendRedirect("/loginsuccessadmin");
        }else
        {
            httpServletResponse.sendRedirect("/loginsuccessuser");
        }


    }

}
