package com.mengxuegu.security.authentication.session;

import com.mengxuegu.security.authentication.CustomAuthenticationFailureHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/8 17:18
 * @desc 当同一个用户的session达到一定数量时，执行该方法
 */
@Slf4j
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException {
        // 退出的用户
        UserDetails userDetails = (UserDetails) sessionInformationExpiredEvent.getSessionInformation().getPrincipal();

        AuthenticationException exception
                = new AuthenticationServiceException(String.format("%s用户在另一台电脑登录，您已被迫下线", userDetails.getUsername()));

        sessionInformationExpiredEvent.getRequest().setAttribute("toAuthentication", true);

        try {
            customAuthenticationFailureHandler.onAuthenticationFailure(sessionInformationExpiredEvent.getRequest(),
                    sessionInformationExpiredEvent.getResponse(), exception);
            log.info("顶替用户======{}", userDetails.getUsername());
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }
}
