package com.waylau.spring.boot.security.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
 
	@Autowired 
	private UserDetailsService userDetailsService;
	@Test
	public void testLoadUserByUsername(){
 
		String username = "waylau";
 
		UserDetails u = userDetailsService.loadUserByUsername(username);
		System.out.print(u.getUsername());
		System.out.print(u.getPassword());
	}

}
