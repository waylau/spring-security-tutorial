package com.waylau.spring.boot.security.service;

import java.util.List;

import com.waylau.spring.boot.security.domain.User;

/**
 * User 服务接口.
 * 
 * @since 1.0.0 2017年3月18日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface UserService {

	void removeUser(Long id);
	
	List<User> listUsers();
}
