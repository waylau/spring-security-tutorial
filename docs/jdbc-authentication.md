# JDBC 认证

本文展示了如何使用 JDBC 的方式来进行认证。在本例，我们将认证信息存储于 H2 数据库中。

在 `role-base-login`项目的基础上，我们构建了一个`jdbc-authentication`项目。

## build.gradle
 
 修改 build.gradle 文件，让我们的`jdbc-authentication`项目成为一个新的项目。

修改内容也比较简单，修改项目名称及版本即可。

```groovy
jar {
	baseName = 'jdbc-authentication'
	version = '1.0.0'
}
```
 
## 处理依赖

添加 `spring-boot-starter-jdbc` 和 `h2`的依赖：

```groovy
// 添加 Spring Boot JDBC 的依赖
compile('org.springframework.boot:spring-boot-starter-jdbc')
 
// 添加 H2 的依赖
runtime('com.h2database:h2:1.4.193')
 ```groovy
 
## 修改配置类

在配置类中，添加成功认证的处理器 AuthenticationSuccessHandler 的实例：

```
.formLogin()   //基于 Form 表单登录验证
	.loginPage("/login").failureUrl("/login-error") // 自定义登录界面
	.successHandler(authenticationSuccessHandler)
```

## 运行

当我们使用“ADMIN”角色的用户登录成功后，会被重定向到`/admins`页面。而使用“USER”角色的用户登录成功后，会被重定向到`/users`页面。