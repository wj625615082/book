package com.wj.books.config;

import com.wj.books.domain.Auth;
import com.wj.books.domain.Permission;
import com.wj.books.domain.Role;
import com.wj.books.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 自定义权限评估器，用于判断用户是否具有访问特定资源的权限
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private RoleService roleService;

    /**
     * 检查用户是否具有指定权限
     *
     * @param authentication 用户认证信息，包含用户的角色和权限
     * @param targetUrl      目标资源的URL
     * @param targetPermission 目标权限
     * @return 如果用户具有指定权限则返回true，否则返回false
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {

        // 获取用户的所有权限
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();

        // 遍历所有权限
        for(GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();

            // 判断用户是否具有访问指定资源的权限
            boolean b = roleService.havePermission(((Auth)authentication.getPrincipal()).getId(),roleName);
            return b;
        }

        // 如果没有找到匹配的权限，返回false
        return false;
    }

    /**
     * 未使用的权限检查方法，始终返回false
     *
     * @param authentication 用户认证信息
     * @param serializable    资源标识符
     * @param s              权限类型
     * @param o              权限值
     * @return 始终返回false
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
