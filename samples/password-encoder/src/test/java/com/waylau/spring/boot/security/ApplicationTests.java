package com.waylau.spring.boot.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	
	@Test
	public void contextLoads() {
	}

	/**
	 * 摘要认证加密算法
	 */
	@Test
	public void testBCryptPasswordEncoder() {
		CharSequence rawPassword = "123456";
		
		PasswordEncoder  encoder = new BCryptPasswordEncoder();
		String encodePasswd = encoder.encode(rawPassword);
		boolean isMatch = encoder.matches(rawPassword, encodePasswd);
		System.out.println("encodePasswd:" + encodePasswd);
		System.out.println(isMatch);
	}
}
