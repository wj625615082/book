package com.wj.books.service.impl;

import com.wj.books.domain.Auth;
import com.wj.books.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * spring security 授权使用
 *
 * @author wujun
 * @date 2025-04-19
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名查询用户信息
        Auth admin = userDetailsRepository.findByUsername(username);

        // 如果用户不存在，则抛出异常
        if (admin == null) {
            throw new UsernameNotFoundException(username);
        }
        // 返回用户信息
        return admin;
    }

}
