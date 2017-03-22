package com.waylau.spring.boot.security.controlller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页控制器.
 * 
 * @since 1.0.0 2017年3月8日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@RestController
public class HelloController {
	
	@GetMapping("/hello")
	public String hello() {
		return "Welcome waylau.com";
	}
 
}
