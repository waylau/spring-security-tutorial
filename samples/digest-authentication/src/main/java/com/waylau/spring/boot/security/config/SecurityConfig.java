package com.waylau.spring.boot.security.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

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
	private UserDetailsService userDetailsService;
	
//	@Bean
//	public DigestAuthenticationFilter getDigestAuthenticationFilter(){
//		return new DigestAuthenticationFilter();
//	}
	
	@Bean
	public DigestAuthenticationEntryPoint getDigestAuthenticationEntryPoint(){
		return new DigestAuthenticationEntryPoint();
	}
 
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
			.httpBasic() // 使用摘要认证
				.and()
			.addFilter(digestAuthenticationFilter(getDigestAuthenticationEntryPoint()))  // 使用摘要认证过滤器
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 无状态
				.and()
			.exceptionHandling().accessDeniedPage("/403"); // 处理异常，拒绝访问就重定向到 403 页面
		http.logout().logoutSuccessUrl("/");   // 成功登出后，重定向到 首页
		http.csrf().ignoringAntMatchers("/h2-console/**"); // 禁用 H2 控制台的 CSRF 防护
		http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的H2 控制台的请求
	}
 
//	@Override
//	@Bean
//	public UserDetailsService userDetailsServiceBean() throws Exception {
//	    return super.userDetailsServiceBean();
//	}

	public DigestAuthenticationFilter digestAuthenticationFilter (
			DigestAuthenticationEntryPoint digestAuthenticationEntryPoint) throws Exception{
		
		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
		digestAuthenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint);
		digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
		return digestAuthenticationFilter;
	}
	/**
	 * 认证信息管理
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
}
