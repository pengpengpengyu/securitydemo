package com.mengxuegu.security.authentication.session;

import com.mengxuegu.result.MengxueguResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangpengyu
 * @version 1.0
 * @date 2020/9/8 16:13
 * @desc session失效后的逻辑处理
 */
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {

    @Override
    public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        //cancleCookie(httpServletRequest, httpServletResponse);
        MengxueguResult result = MengxueguResult.build(HttpStatus.UNAUTHORIZED.value(), "登录已超时，请重新登录");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(result.toJsonString());
    }

    /**
     * 清除cookie 参考remember me功能
     *
     * @param request
     * @param response
     */
    private void cancleCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));

        response.addCookie(cookie);
    }

    /**
     * 获取cookie路径
     *
     * @param request
     * @return
     */
    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}