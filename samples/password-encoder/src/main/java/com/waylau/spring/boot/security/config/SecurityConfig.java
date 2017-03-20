package com.waylau.spring.boot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

/**
 * Spring Security 配置类.
 * 
 * @since 1.0.0 2017年3月8日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String DIGEST_KEY = "waylau.com";
	private static final String DIGEST_REALM_NAME = "spring security tutorial";
	private static final int DIGEST_NONCE_VALIDITY_SECONDS = 240; // 过期时间 4 分钟

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 自定义 DigestAuthenticationEntryPoint
	 * 
	 * @return
	 */
	@Bean
	public DigestAuthenticationEntryPoint getDigestAuthenticationEntryPoint() {
		DigestAuthenticationEntryPoint digestEntryPoint = new DigestAuthenticationEntryPoint();
		digestEntryPoint.setKey(DIGEST_KEY);
		digestEntryPoint.setRealmName(DIGEST_REALM_NAME);
		digestEntryPoint.setNonceValiditySeconds(DIGEST_NONCE_VALIDITY_SECONDS);
		return digestEntryPoint;
	}

	/**
	 * 摘要认证过滤器
	 * 
	 * @param digestAuthenticationEntryPoint
	 * @return
	 * @throws Exception
	 */
	@Bean
	public DigestAuthenticationFilter digestAuthenticationFilter(
			DigestAuthenticationEntryPoint digestAuthenticationEntryPoint) throws Exception {

		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
		digestAuthenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint);
		digestAuthenticationFilter.setUserDetailsService(userDetailsService);
		return digestAuthenticationFilter;
	}

	/**
	 * 自定义配置
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll() // 都可以访问
				.antMatchers("/h2-console/**").permitAll() // 都可以访问
				.antMatchers("/users/**").hasRole("USER") // 需要相应的角色才能访问
				.antMatchers("/admins/**").hasRole("ADMIN") // 需要相应的角色才能访问
				.and().addFilter(digestAuthenticationFilter(getDigestAuthenticationEntryPoint())) // 使用摘要认证过滤器
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 无状态
				.and().exceptionHandling().accessDeniedPage("/403") // 处理异常，拒绝访问就重定向到
																	// 403 页面
				.authenticationEntryPoint(getDigestAuthenticationEntryPoint()); // 自定义
																				// AuthenticationEntryPoint
		http.csrf().ignoringAntMatchers("/h2-console/**"); // 禁用 H2 控制台的 CSRF 防护
		http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的H2 控制台的请求
	}
}
