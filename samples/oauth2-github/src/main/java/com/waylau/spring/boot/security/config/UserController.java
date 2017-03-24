package com.waylau.spring.boot.security.config;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.waylau.spring.boot.security.domain.User;
 

/**
 * 用户控制器.
 * 
 * @author <a href="https://waylau.com">Way Lau</a>
 * @date 2017年2月26日
 */
@RestController
@RequestMapping("/")
public class UserController {
	
    @GetMapping("/user")
    public Principal user(Principal principal) {
        System.out.println(principal.toString());
        return principal;
    }

	/**
	 * 查询所用用户
	 * @return
	 */
	@GetMapping("/users")
	@PreAuthorize("hasAuthority('ROLE_USER')")  // 指定角色权限才能操作方法
	public ModelAndView listUsers(Model model) {

		List<User> list = new ArrayList<>();	// 当前所在页面数据列表
		list.add(new User("waylau",29));
		list.add(new User("老卫",30));
		model.addAttribute("title", "用户管理");
		model.addAttribute("userList", list);
		return new ModelAndView("users/list", "userModel", model);
	}
	
	/**
	 * 查询所用管理员用户
	 * @return
	 */
	@GetMapping("/admins")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
	public ModelAndView listAdmins(Model model) {

		List<User> list = new ArrayList<>();	// 当前所在页面数据列表
		list.add(new User("waylau",29));
		list.add(new User("老卫",30));
		model.addAttribute("title", "管理员管理");
		model.addAttribute("userList", list);
		return new ModelAndView("users/list", "userModel", model);
	}
}
