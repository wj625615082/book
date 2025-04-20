package com.wj.books.aspect;

import com.wj.books.domain.AdminLog;
import com.wj.books.service.AdminLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 日志切面类
 *
 * @author wj
 * @date 2025-4-20
 */

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AdminLogService adminLogService;

    @Pointcut("@annotation(com.wj.books.annotation.Loggable)")
    public void loggableMethods() {}

    @AfterReturning(pointcut = "loggableMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String admin = "admin"; // 这里可以替换为实际的管理员获取方式
        String path = request.getRequestURI();
        Date createTime = new Date();
        String action = "执行了 " + className + "." + methodName;
        Integer type = 1; // 根据实际情况设置操作类型
        String logResult = result != null ? result.toString() : "成功";
        String ip = request.getRemoteAddr();

        AdminLog adminLog = new AdminLog();
        adminLog.setAdmin(admin);
        adminLog.setPath(path);
        adminLog.setCreateTime(createTime);
        adminLog.setAction(action);
        adminLog.setType(type);
        adminLog.setResult(logResult);
        adminLog.setIp(ip);

        adminLogService.save(adminLog);
    }
}
