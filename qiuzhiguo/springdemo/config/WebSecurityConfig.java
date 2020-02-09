package com.qiuzhiguo.springdemo.config;

import com.qiuzhiguo.springdemo.service.MyUserDetailsService;
import com.qiuzhiguo.springdemo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;



@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private CustomLoginFailureHandler customLoginFailureHandler;

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;




    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService()).passwordEncoder(new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String)rawPassword));
            }});

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }



    //实现InvalidSessionStrategy接口
    public class MyInvalidSessionStrategy implements InvalidSessionStrategy{
        @Override
        public void onInvalidSessionDetected(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException,ServletException {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.getWriter().write("session 无效");
        }
    }

    /**
     * Session过期处理策略
     *
     * @author Logan
     * @createDate 2019-02-13
     * @version 1.0.0
     *
     */
    public class SessionInformationExpiredStrategyImpl implements SessionInformationExpiredStrategy {

        @Override
        public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
            System.out.println("session 过期");
        }

    }



    @Override
    public void configure(WebSecurity web) throws Exception {
        //配置静态文件不需要认证
        web.ignoring().antMatchers("/static/**");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and().authorizeRequests()
                .antMatchers("/admin/api/**","/admin/page/").hasRole("ADMIN")
                .antMatchers("/user/api/**","/user/page/").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .antMatchers("/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable().headers().frameOptions().sameOrigin().and() //csrf()方法是Spring Scurity提供的跨站请求伪造防护功能
                .formLogin()
                .loginPage("/mylogin")
                .loginProcessingUrl("/login")
                .successHandler(customLoginSuccessHandler)
                .failureHandler(customLoginFailureHandler)
                .permitAll()
                .and()
                .logout()
                    //指定接受注销请求的路由
                    .logoutUrl("myLogout")
                    //注销成功后,重定向到该路径下
                    .logoutSuccessHandler(myLogoutSuccessHandler)
                    //注销成功的处理方式,不同于logoutSuccessUrl的重定向,logoutSuccessHander更加灵活,此处不加,详细细节看书
                    //使该用户的HttpSession失效
                    .invalidateHttpSession(true)
                    //注销成功,删除指定的cookie
                    .deleteCookies("cookie1","cookie2")
                .and()
                .rememberMe().userDetailsService(myUserDetailsService)
                .tokenRepository(persistentTokenRepository())//设置操作表的Repository
                .tokenValiditySeconds(60*60*24*7)//设置记住我的时间为7天;

                .and()
                .sessionManagement()

                .maximumSessions(1)
                .expiredSessionStrategy(new SessionInformationExpiredStrategyImpl());
    }

}
