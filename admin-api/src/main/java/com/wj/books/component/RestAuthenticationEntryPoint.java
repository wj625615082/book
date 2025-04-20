package com.wj.books.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wj.books.commons.CommonResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.wj.books.commons.CommonResult.PASS_ERR;

/**
 * 登录失败返回结果
 *
 * @author wujun
 * @date 2025-04-19
 *
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String result;
        ObjectMapper objectMapper = new ObjectMapper();
        // 用户不存在和密码错误都提示【用户名或密码错误】
        if (authException instanceof UsernameNotFoundException
                || authException instanceof BadCredentialsException) {
            result = objectMapper.writeValueAsString(new CommonResult().failed(PASS_ERR, "用户名或密码错误"));
        } else {
            result = objectMapper.writeValueAsString(new CommonResult().failed(-1, "未授权？"));
        }
        response.getWriter().println(result);
        response.getWriter().flush();
    }

}
