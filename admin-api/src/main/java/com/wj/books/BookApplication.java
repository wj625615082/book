package com.wj.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * BookApplication 是书籍管理系统的主应用程序类
 * 它使用了Spring Boot的自动配置功能，并集成了缓存和切面代理
 *
 * @author wujun
 * @date 2025-04-19
 */
@EnableCaching // 启用Spring的缓存功能，用于加速频繁查询等操作
@SpringBootApplication // 标识这是一个Spring Boot应用，自动扫描当前包及其子包中的组件
@EnableAspectJAutoProxy // 启用AspectJ自动代理，用于支持切面编程
public class BookApplication {

    /**
     * 主程序入口点
     * 负责启动Spring Boot应用
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

}
