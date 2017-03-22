package com.waylau.spring.boot.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer  // 启用授权服务器，与 AuthorizationServerConfigurer 结合使用
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
