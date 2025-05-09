package com.wj.books.component;

import com.wj.books.domain.AdminLog;
import com.wj.books.repository.AdminLogRepository;
import com.wj.books.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登录日志记录
 *
 * @author wujun
 * @date 2025-04-19
 *
 */
@Component
public class AdminLogHelper {

    private final AdminLogRepository adminLogRepository;

    @Autowired
    public AdminLogHelper(AdminLogRepository adminLogRepository) {
        this.adminLogRepository = adminLogRepository;
    }

    private final static Integer LOG_TYPE_GENERAL = 0;
    private final static Integer LOG_TYPE_SECURITY = 1;
    private final static Integer LOG_TYPE_OTHER = 9;

    public void loginSucceed() {
        log(LOG_TYPE_SECURITY, "登陆", "成功");
    }

    public void logoutSucceed() {
        log(LOG_TYPE_SECURITY, "登出", "成功");
    }

    private void log(Integer type, String action, String result) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        AdminLog adminLog = new AdminLog();
        adminLog.setAdmin(username);
        adminLog.setCreateTime(new Date());
        adminLog.setAction(action);
        adminLog.setType(type);
        adminLog.setResult(result);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException();
        }
        HttpServletRequest request = attributes.getRequest();
        adminLog.setIp(IpUtil.getIpAddress(request));
        adminLog.setPath(request.getRequestURI());
        adminLogRepository.save(adminLog);
    }

}
