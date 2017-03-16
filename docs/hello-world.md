# Hello World
 
依照管理，我们从一个 Hello World 项目入手。

我们新家了一个名为`hello-world`的 Gradle 项目。

基于Form 表单的登录认证。


## 配置类


这段代码内容很少，但事实上已经做了很多的默认安全验证，包括：

* 访问应用中的每个URL都需要进行验证
* 生成一个登陆表单
* 允许用户使用用户名和密码来登陆
* 允许用户注销
* [CSRF](https://en.wikipedia.org/wiki/Cross-site_request_forgery)攻击拦截
* [Session Fixation](https://en.wikipedia.org/wiki/Session_fixation) 攻击
* 安全 Header 集成
	* 启用 [HTTP Strict Transport Security](https://en.wikipedia.org/wiki/HTTP_Strict_Transport_Security)
	* [X-Content-Type-Options](https://msdn.microsoft.com/en-us/library/ie/gg622941(v=vs.85).aspx) 集成
	* Cache Control（ 缓存控制 ）
	* [X-XSS-Protection](https://msdn.microsoft.com/en-us/library/dd565647(v=vs.85).aspx) 集成
	* 集成 X-Frame-Options 来防止 [Clickjacking](https://en.wikipedia.org/wiki/Clickjacking)
* 集成了以下 Servlet API :
	* [HttpServletRequest#getRemoteUser()](https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletRequest.html#getRemoteUser())
	* [HttpServletRequest.html#getUserPrincipal()](https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletRequest.html#getUserPrincipal())
	* [HttpServletRequest.html#isUserInRole(java.lang.String)](https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletRequest.html#isUserInRole(java.lang.String))
	* [HttpServletRequest.html#login(java.lang.String, java.lang.String)](https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletRequest.html#login(java.lang.String,%20java.lang.String))
	* [HttpServletRequest.html#logout()](https://docs.oracle.com/javaee/6/api/javax/servlet/http/HttpServletRequest.html#logout())
