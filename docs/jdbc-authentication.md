# JDBC 认证

本文展示了如何使用 JDBC 的方式来进行认证。在本例，我们将认证信息存储于 H2 数据库中。

在 `method-security`项目的基础上，我们构建了一个`jdbc-authentication`项目。

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
 
## 修改配置文件

修改配置文件 application.properties :

```
# 使用 H2 控制台
spring.h2.console.enabled=true
```

## 修改配置类 SecurityConfig

在配置类中，添加对 H2 的过滤策略：

```
http
	.authorizeRequests()
		.antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()  // 都可以访问
		.antMatchers("/h2-console/**").permitAll()  // 都可以访问
		.antMatchers("/users/**").hasRole("USER")   // 需要相应的角色才能访问
		.antMatchers("/admins/**").hasRole("ADMIN")   // 需要相应的角色才能访问
		.and()
	.formLogin()   //基于 Form 表单登录验证
		.loginPage("/login").failureUrl("/login-error") // 自定义登录界面
		.and()
	.exceptionHandling().accessDeniedPage("/403"); // 处理异常，拒绝访问就重定向到 403 页面
http.logout().logoutSuccessUrl("/");   // 成功登出后，重定向到 首页
http.csrf().ignoringAntMatchers("/h2-console/**"); // 禁用 H2 控制台的 CSRF 防护
http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的H2 控制台的请求
```

其中：

* `http.csrf().ignoringAntMatchers` ： 用于配置匹配的 URL，不进行 CSRF 防护。这里，我们不需要对 H2 控制台做 CSRF 防护
* `http.headers().frameOptions().sameOrigin()` ： 用于配置同源策略。允许来自同一来源的H2 控制台的请求

## 使用Spring JDBC初始化数据库

Spring JDBC 有一个 DataSource 初始化功能。 Spring Boot 默认启用它，并从标准位置（在类路径的根目录中）的`schema.sql` 和 `data.sql` 脚本中加载SQL。此外，Spring Boot将加载`schema-${platform}.sql`和`data-${platform}.sql`文件（如果存在的话），其中 platform 是 `spring.datasource.platform`的值，例如。您可以选择将其设置为数据库的供应商名称（hsqldb、h2、oracle、mysql、postgresql 等）。 Spring Boot 默认启用了 Spring JDBC 初始化程序中的快速失败（fail-fast）功能，因此，如果脚本导致异常，应用程序将无法启动。可以通过设置`spring.datasource.schema`和`spring.datasource.data`来更改脚本位置，如果`spring.datasource.initialize=false`，则不会处理任何位置的脚本。

要禁用快速失败（fail-fast）功能，您可以设置`spring.datasource.continue-on-error=true`。这在应用程序成熟并部署了几次后可能很有用，因为插入失败意味着数据已经存在，因此不需要阻止应用程序运行。

如果要在 JPA 应用程序（如使用Hibernate）中使用schema.sql初始化，那么如果 Hibernate 尝试创建相同的表，`ddl-auto=create-drop`将导致错误。要避免这些错误，请将 ddl-auto 显式设置为“”（首选）或“none”。无论是否使用`ddl-auto=create-drop`，您都可以使用 data.sql 初始化新数据。

我们的表结构写在了 schema.sql 中：

```sql
create table users (
  username varchar(256),
  password varchar(256),
  enabled boolean
);

create table authorities (
  username varchar(256),
  authority varchar(256)
);
```

我们的数据写在了 data.sql 中：

```sql
insert into users (username, password, enabled) values ('waylau', '123456', true);
insert into users (username, password, enabled) values ('admin', '123456', true);

insert into authorities (username, authority) values ('waylau', 'ROLE_USER');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
```


## 访问 H2 控制台

设置 JDBC URL为 `jdbc:h2:mem:testdb`：
 

![](../images/method-security/h2.jpg)

可以看到我们新建的数据库表和初始化的数据：

![](../images/method-security/h2-table.jpg)