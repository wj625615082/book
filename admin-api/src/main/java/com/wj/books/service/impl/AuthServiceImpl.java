package com.wj.books.service.impl;

import com.wj.books.component.AdminLogHelper;
import com.wj.books.component.JwtTokenUtil;
import com.wj.books.domain.Admin;
import com.wj.books.domain.Permission;
import com.wj.books.domain.Role;
import com.wj.books.dto.AuthInfoResult;
import com.wj.books.dto.LoginParam;
import com.wj.books.dto.LoginResult;
import com.wj.books.repository.AdminRepository;
import com.wj.books.repository.UserDetailsRepository;
import com.wj.books.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 鉴权服务实现类
 *
 * @author wujun
 * @date 2025-04-19
 */
@Service
public class AuthServiceImpl implements AuthService {

    // 用于密码编码
    private final PasswordEncoder passwordEncoder;
    // 用于JWT令牌的工具类
    private final JwtTokenUtil jwtTokenUtil;
    // 用户详细信息的仓库
    private final UserDetailsRepository userDetailsRepository;
    // 管理员日志帮助类
    private final AdminLogHelper adminLogHelper;
    // 管理员的仓库
    private final AdminRepository adminRepository;

    // 存储权限的映射，用于快速查找权限
    private HashMap<String, String> permissionsMap = null;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           JwtTokenUtil jwtTokenUtil,
                           UserDetailsRepository userDetailsRepository,
                           AdminLogHelper adminLogHelper,
                           AdminRepository adminRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsRepository = userDetailsRepository;
        this.adminLogHelper = adminLogHelper;
        this.adminRepository = adminRepository;
    }

    /**
     * 用户登录方法
     *
     * @param loginParam 包含用户名和密码的登录参数
     * @return 登录结果，包括JWT令牌
     * @throws BadCredentialsException 如果密码不正确
     */
    @Override
    public LoginResult login(LoginParam loginParam) {
        UserDetails userDetails = userDetailsRepository.findByUsername(loginParam.getUsername());
        if (!passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
        adminLogHelper.loginSucceed();
        // TODO 之后增加登陆时间
        return new LoginResult(token);
    }

    /**
     * 获取管理员信息
     *
     * @param username 管理员的用户名
     * @return 包含管理员角色和权限的信息结果
     */
    @Override
    //@Cacheable(value = "adminInfo", key = "#username")
    public AuthInfoResult getAdminInfo(String username) {
        Admin admin = adminRepository.findByUsername(username);
        AuthInfoResult result = new AuthInfoResult();
        result.setUsername(username);
        result.setAvatar(admin.getAvatar());
        Set<String> perms = new HashSet<>();
        Set<String> roles = new HashSet<>();
        for (Role role : admin.getRoles()) {
            roles.add(role.getName());
            for (Permission permission : role.getPermissions()) {
                perms.add(permission.getPermission());
            }
        }
        result.setRoles(roles.toArray(new String[0]));
        result.setPerms(perms.toArray(new String[0]));
        return result;
    }

}
