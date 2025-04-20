#szhn笔试图书管理系统

原创版权，禁止复制。

系统架构：
    开发框架：Spring Boot 2.1.4、jdk 1.8
    数据库：H2数据库（内置）
    安全框架：Spring Security
    缓存：Redis（内置）、Spring cache
    日志管理：内置表
    前端框架：Vue.js、node.js、element-ui

项目功能：
    1.对图书的增、删改、查功能。
    2.有用户登录控制、权限控制、日志记录等功能。

运行：
    1.后端：运行books-master\admin-api\src\main\java\com\wj\books\BookApplication类。
    2.前端：运行books-master\ui\package.json文件"init"和"dev"。


考试要求说明：
    1.图书管理系统增删改，详见此类：BookController.java
    2.用户登录控制，运行前端，打开http://localhost:9528/，可见登录界面。Spring Security配置可看此类WebSecurityConfig
    3.权限控制，前后端都有权限控制。前端可看路由文件books-master\ui\src\router\index.js中“perms: ['book.manage']”此写法，具备按钮级别权限控制，如”v-permission="['sys:admin:create']"。
                后端可加注解进行接口权限控制，如”@PreAuthorize("hasPermission('/book', 'book.manage')")“，控制逻辑修改可见此类CustomPermissionEvaluator。
    4.分层，代码为分层结构。
    5.日志，本项目用表记录日志。详见此类：LogController.java。使用方法可以使用注解形式，也可使用方法调用形式。如注解形式在方法体上加上注解@Loggable 即可。接口形式可以调用此方法：adminLogService.save(adminLog);
    6.缓存，本项目有内置redis。可用Spring cache语法调用，如：@Cacheable(value = "book", key = "#id")
    7.数据库，数据库采用内置h2数据库，后台页面地址，http://localhost:8082/h2-console/，用户名：root，密码：123456。可通过application*.yml文件修改。
    8.基于环境配置分离，可在application-dev.yml、application-test.yml、application-prod.yml文件中修改。
    9.swagger，可访问http://localhost:8082/swagger-ui.html使用。