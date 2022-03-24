package com.frame.interceptor;

import com.frame.exception.MyException;
import com.frame.helper.JwtHelper;
import com.frame.result.ResultCodeEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        int userId = this.getUserId(request);
        if(userId==-1) {
            throw new MyException(ResultCodeEnum.LOGIN_AUTH);
        }

        return true;
    }

    /**
     * 获取当前登录用户id
     * @param request
     * @return
     */
    private int getUserId(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(!StringUtils.isEmpty(token)) {
            return JwtHelper.getUserId(token);
        }
        return -1;
    }
}
