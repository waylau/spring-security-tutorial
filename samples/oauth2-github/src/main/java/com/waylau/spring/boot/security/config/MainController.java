package com.waylau.spring.boot.security.config;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页控制器.
 * 
 * @since 1.0.0 2017年3月8日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Controller
public class MainController {
	
	@GetMapping("/")
	public String root() {
		return "redirect:/index";
	}
	
	@GetMapping("/index")
	public String index(Principal principal, Model model) {
	    if(principal == null ){
	    	return "index";
	    }
		System.out.println(principal.toString());
		model.addAttribute("principal", principal);
		return "index";
	}

	@GetMapping("/403")
	public String accesssDenied() {
		return "403";
	}
}
