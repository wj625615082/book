package com.wj.books.service;

import com.wj.books.dto.AuthInfoResult;
import com.wj.books.dto.LoginParam;
import com.wj.books.dto.LoginResult;

/**
 * @author wujun
 * @date 2025-04-19
 */
public interface AuthService {

    /**
     * 管理员登陆
     *
     * @param loginParam 登陆参数，包含username和password
     * @return token
     */
    LoginResult login(LoginParam loginParam);

    /**
     * 根据用户名获取管理员信息
     *
     * @param username 用户名
     * @return 管理员
     */
    AuthInfoResult getAdminInfo(String username);
}
