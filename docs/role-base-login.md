# 基于角色的登录

本文展示了如何根据不同的用户角色，在登录之后来重定向到不同的页面。

在 `method-security`项目的基础上，我们构建了一个`role-base-login`项目。

## build.gradle
 
 修改 build.gradle 文件，让我们的`role-base-login`项目成为一个新的项目。

修改内容也比较简单，修改项目名称及版本即可。

```groovy
jar {
	baseName = 'role-base-login'
	version = '1.0.0'
}
```
 
## 权限处理器

新增`com.waylau.spring.boot.security.auth`包，用于存放权限处理器相关的类。

新建 AuthenticationSuccessHandler ，继承自 `org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler`

```
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to "
					+ targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	private String determineTargetUrl(Authentication authentication) {
		String targetUrl = null;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> roles = new ArrayList<String>();

		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}

		// 根据不同的角色，重定向到不同页面
		if (isAdmin(roles)) {
			targetUrl = "/admins";
		} else if (isUser(roles)) {
			targetUrl = "/users";
		} else {
			targetUrl = "/403";
		}

		return targetUrl;
	}
	
	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_USER")) {
			return true;
		}
		return false;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}
 
}
```

重写了 handle()方法，这个方法简单地使用 RedirectStrategy 来重定向，由自定义的 determineTargetUrl 方法返回URL。此方法提取当前认证对象用户记录的角色，然后构造基于角色有相应的URL。最后由负责 Spring Security 框架内的所有重定向的 RedirectStrategy ，将请求重定向到指定的URL。

## 修改配置类

在配置类中，添加成功认证的处理器 AuthenticationSuccessHandler 的实例：

```
.formLogin()   //基于 Form 表单登录验证
	.loginPage("/login").failureUrl("/login-error") // 自定义登录界面
	.successHandler(authenticationSuccessHandler)
```

## 运行

当我们使用“ADMIN”角色的用户登录成功后，会被重定向到`/admins`页面。而使用“USER”角色的用户登录成功后，会被重定向到`/users`页面。