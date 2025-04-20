package com.wj.books.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wj.books.commons.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拒绝访问处理器
 *
 * @author wujun
 * @date 2025-04-19
 *
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(new ObjectMapper().writeValueAsString(new CommonResult().failed(-1, "为什么？？")));
        response.getWriter().flush();
    }
}
