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
	private static final CharSequence rawPassword = "123456";
	
	@Test
	public void contextLoads() {
		PasswordEncoder  encoder = new BCryptPasswordEncoder(4);
		String encodePasswd = encoder.encode(rawPassword);
		boolean isMatch = encoder.matches(rawPassword, encodePasswd);
		System.out.println("code:" + encodePasswd);
		System.out.println(isMatch);
	}

}
