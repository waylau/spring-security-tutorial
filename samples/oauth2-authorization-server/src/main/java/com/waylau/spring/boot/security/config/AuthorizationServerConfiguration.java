package com.waylau.spring.boot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Authorization Server 配置类.
 * 
 * @since 1.0.0 2017年3月22日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
	private static final String REALM = "spring security tutorial";
 
	@Autowired
	private ClientDetailsService clientDetailsService;

	/**
	 * 定义授权、令牌端点和令牌服务
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients().realm(REALM);
	}

	/**
	 * 定义客户详细服务信息。
	 * 可以采用内存方式或者JDBC方式定义客户信息
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}

	/**
	 * 定义令牌端点的安全约束
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	}

}
