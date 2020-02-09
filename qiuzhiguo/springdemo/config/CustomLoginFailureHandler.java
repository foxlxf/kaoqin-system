package com.qiuzhiguo.springdemo.config;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public CustomLoginFailureHandler(){
        this.setDefaultFailureUrl("/loginerror");
    }
    //在这里面可以处理详细的失败信息，和上面的一般不要同时都使用
   /* public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        CustomLoginFailureHandler customLoginFailureHandler = new CustomLoginFailureHandler();
        String defaultFailureUrl = "/login";
        if(exception instanceof UsernameNotFoundException){
            defaultFailureUrl += "?error="+ ExceptionCode.USERNAMEERROR;
        } else if(exception instanceof BadCredentialsException){
            defaultFailureUrl += "?error="+ ExceptionCode.PWDERROR;
        } else{
            defaultFailureUrl += "?error="+ ExceptionCode.SYSTEMERROR;
        }

        saveException(request, exception);
        logger.debug("Redirecting to " + defaultFailureUrl);
        customLoginFailureHandler.getRedirectStrategy().sendRedirect(request, response, defaultFailureUrl);
    }*/
}

