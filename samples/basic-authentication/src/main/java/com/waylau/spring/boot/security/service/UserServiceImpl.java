package com.waylau.spring.boot.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.waylau.spring.boot.security.domain.User;
import com.waylau.spring.boot.security.repository.UserRepository;

/**
 * User 服务.
 * 
 * @since 1.0.0 2017年3月18日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.security.service.UserService#removeUser(java.lang.Long)
	 */
	@Override
	public void removeUser(Long id) {
		userRepository.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.security.service.UserService#listUsers()
	 */
	@Override
	public List<User> listUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

}
