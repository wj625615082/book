# SZHN 笔试 - 图书管理系统

**原创版权，禁止复制。**

## 系统架构

- **开发框架**：Spring Boot 2.1.4、JDK 1.8
- **数据库**：H2数据库（内置）
- **安全框架**：Spring Security
- **缓存**：Redis（内置）、Spring Cache
- **日志管理**：内置表
- **前端框架**：Vue.js、Node.js、Element-UI

## 项目功能

1. 对图书的增、删、改、查功能。
2. 用户登录控制、权限控制、日志记录等功能。

## 运行指南

### 后端
运行路径：`books-master\admin-api\src\main\java\com\wj\books\BookApplication` 类。

### 前端
运行文件：`books-master\ui\package.json`  
执行命令：`init` 和 `dev`。

## 考试要求说明

1. **图书管理功能**
    - 实现增删改查，详见：`BookController.java`

2. **用户登录控制**
    - 访问前端：`http://localhost:9528/`
    - Spring Security 配置类：`WebSecurityConfig`

3. **权限控制**
    - **前端**：路由文件 `books-master\ui\src\router\index.js`  
      示例：`perms: ['book.manage']`，按钮级权限如 `v-permission="['sys:admin:create']"`
    - **后端**：注解控制，如 `@PreAuthorize("hasPermission('/book', 'book.manage')")`  
      逻辑修改类：`CustomPermissionEvaluator`

4. **分层结构**
    - 代码采用分层架构设计。

5. **日志管理**
    - 日志记录表实现，详见：`LogController.java`
    - **使用方式**：
        - 注解形式：`@Loggable`
        - 方法调用：`adminLogService.save(adminLog);`

6. **缓存机制**
    - 内置 Redis，支持 Spring Cache 语法，如：
      ```java
      @Cacheable(value = "book", key = "#id")
      ```

7. **数据库配置**
    - 内置 H2 数据库控制台：`http://localhost:8082/h2-console/`
        - 用户名：`root`
        - 密码：`123456`
    - 配置文件：`application*.yml`

8. **多环境配置**
    - 修改文件：`application-dev.yml`、`application-test.yml`、`application-prod.yml`

9. **Swagger 接口文档**
    - 访问地址：`http://localhost:8082/swagger-ui.html`