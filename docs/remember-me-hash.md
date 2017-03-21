# Remember-Me（记住我）认证：基于散列的令牌方法

## Remember-Me（记住我）认证
 
Remember-Me或持久的登录身份验证是指网站能够记住身份之间的会话。这通常是通过发送 cookie 到浏览器，cookie 在未来会话中被检测到，并导致自动登录发生。Spring Security 为这些操作提供了必要的钩子，并且有两个具体的实现。

* 使用散列来保存基于 cookie 的令牌的安全性
* 使用数据库或其他持久存储机制来存储生成的令牌


需要注意的是，这些实现都需要`UserDetailsService`。如果您使用的是一种身份验证提供程序不使用`UserDetailsService`（例如，LDAP 程序），这样该机制就不会正常工作，除非在你的应用程序上下文中有 `UserDetailsService` bean。

## 简单的基于散列的令牌方法

这种方法使用散列实现一个有用的 Remember-Me 的策略。其本质是，在认证成功后，cookie 被发送到浏览器进行交互。 cookie 的组成如下：

```
base64(username + ":" + expirationTime + ":" +
md5Hex(username + ":" + expirationTime + ":" password + ":" + key))

username:         UserDetailsService 中的身份标识
password:         UserDetails 中的密码
expirationTime:   随机数到期的日期和时间，以毫秒为单位
key:              用于防止随机数标记被修改的私钥
```

因此，Remember-Me 令牌仅适用于指定的期间，并且提供的用户名、密码和密钥不会更改。值得注意的是，这有一个潜在的安全问题，即任何用户代理只要捕获了  Remember-Me 令牌就能一直使用直到令牌过期。这是与摘要验证存在的相同的问题。如果一个认证主体意识到令牌已被截获，他们可以通过密码，来将之前的 Remember-Me 令牌作废。如果需要更重要的安全性，您应该使用下一节中描述的方法。另外 Remember-Me 的服务根本不应该使用。
 
## TokenBasedRememberMeServices

TokenBasedRememberMeServices 产生 RememberMeAuthenticationToken，并由 RememberMeAuthenticationProvider 处理。这种身份验证提供者 与 TokenBasedRememberMeServices 之间共享 key 。此外，TokenBasedRememberMeServices 需要从它可以检索签名比较目的的用户名和密码 UserDetailsService ，生成的 RememberMeAuthenticationToken 包含正确的 GrantedAuthority。 美国一些注销命令应该被应用，无效的Cookie如果用户要求提供。tokenbasedremembermeservices还实现了Spring Security的LogoutHandler界面，可以用logoutfilter有饼干自动清除。
在应用程序上下文中需要记住的ME服务所需的bean如下：