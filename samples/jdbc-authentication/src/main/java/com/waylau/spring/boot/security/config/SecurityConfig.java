package com.waylau.spring.boot.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 配置类.
 * 
 * @since 1.0.0 2017年3月8日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 启用方法安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	/**
	 * 自定义配置
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
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
	}
	
	/**
	 * 认证信息管理
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}
}
