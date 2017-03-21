# 通用密码加密

在“摘要认证的密码加密”一节，我们讲到了如何在摘要认证中使用密码加密。但同时也提到了一些限制，比如，该加密方式只能适用于摘要认证，而且摘要认证，只能采用该种方式进行密码加密。那么，我们如何在其他认证类型（如基本认证、Form表单认证）中进行用户信息的加密呢？本节为你揭开谜底。

 在 `basic-authentication`项目的基础上，我们构建了一个`password-encoder`项目。


## build.gradle
 
 修改 build.gradle 文件，让我们的`password-encoder`项目成为一个新的项目。

修改内容也比较简单，修改项目名称及版本即可。

```groovy
jar {
	baseName = 'password-encoder'
	version = '1.0.0'
}
```

##  MD5 算法

任何一个正式的企业应用中，都不会在数据库中使用明文来保存密码的，我们在之前的章节中都是为了方便起见没有对数据库中的用户密码进行加密，这在实际应用中是极为幼稚的做法。可以想象一下，只要有人进入数据库就可以看到所有人的密码，这是一件多么恐怖的事情，为此我们至少要对密码进行加密，这样即使数据库被攻破，也可以保证用户密码的安全。

最常用的方法是使用MD5算法对密码进行摘要加密，这是一种单项加密手段，无法通过加密后的结果反推回原来的密码明文。

首先我们要把数据库中原来保存的密码使用MD5进行加密：

```
123456  ---MD5---> e10adc3949ba59abbe56e057f20f883e
```
        
现在密码部分已经面目全非了，即使有人攻破了数据库，拿到这种“乱码”也无法登陆系统窃取客户的信息。

## 盐值加密

实际上，上面的实例在现实使用中还存在着一个不小的问题。虽然 MD5 算法是不可逆的，但是因为它对同一个字符串计算的结果是唯一的，所以一些人可能会使用“字典攻击”的方式来攻破 MD5 加密的系统。这虽然属于暴力解密，却十分有效，因为大多数系统的用户密码都不会很长。

实际上，大多数系统都是用 admin 作为默认的管理员登陆密码，所以，当我们在数据库中看到“21232f297a57a5a743894a0e4a801fc3”时，就可以意识到 admin 用户使用的密码了。因此，MD5 在处理这种常用字符串时，并不怎么奏效。

```
admin  ---MD5---> 21232f297a57a5a743894a0e4a801fc3 
```

为了解决这个问题，我们可以使用盐值加密“salt-source”。

修改配置文件：

```
```
        
在password-encoder下添加了salt-source，并且指定使用 username 作为盐值。

盐值的原理非常简单，就是先把密码和盐值指定的内容合并在一起，再使用 MD5 对合并后的内容进行演算，这样一来，就算密码是一个很常见的字符串，再加上用户名，最后算出来的 MD5 值就没那么容易猜出来了。因为攻击者不知道盐值的值，也很难反算出密码原文。

我们这里将每个用户的 username 作为盐值，最后数据库中的密码部分就变成了这样：

```
admin123456 ---MD5---> a66abb5684c45962d887564f08346e8d
```

## PasswordEncoder 的实现

加密实现方式都实现自`org.springframework.security.crypto.password.PasswordEncoder`接口，有AbstractPasswordEncoder、BCryptPasswordEncoder、 NoOpPasswordEncoder、 Pbkdf2PasswordEncoder、SCryptPasswordEncoder、StandardPasswordEncoder。常用的有：

* NoOpPasswordEncoder：按原文本处理，相当于不加密。
* StandardPasswordEncoder：1024次迭代的 SHA-256 散列哈希加密实现，并使用一个随机8字节的salt。
* BCryptPasswordEncoder：使用BCrypt的强散列哈希加密实现，并可以由客户端指定加密的强度strength，强度越高安全性自然就越高，默认为10.

在Spring Security 的注释中，明确写明了如果是开发一个新的项目，BCryptPasswordEncoder 是较好的选择。本节示例也是采用 BCryptPasswordEncoder 方式。

```java
@Autowired
private PasswordEncoder passwordEncoder;

@Bean  
public PasswordEncoder passwordEncoder() {  
    return new BCryptPasswordEncoder();   // 使用 BCrypt 加密
}  
```

另外，我们需要自定义一个  DaoAuthenticationProvider，来将我们的加密方式进行注入：

```java
@Autowired
private AuthenticationProvider authenticationProvider;

@Bean  
public AuthenticationProvider authenticationProvider() {  
	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	authenticationProvider.setUserDetailsService(userDetailsService);
	authenticationProvider.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
    return authenticationProvider;  
}  
```


同时，将上述的 DaoAuthenticationProvider 设置进  AuthenticationManagerBuilder :

```java
@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(authenticationProvider);
}
```

## 生成加密后的密码

具体生成的密码的代码可以看 ApplicationTests.java:

```java
@Test
public void testBCryptPasswordEncoder() {
	
	CharSequence rawPassword = "123456";
	
	PasswordEncoder  encoder = new BCryptPasswordEncoder();
	String encodePasswd = encoder.encode(rawPassword);
	boolean isMatch = encoder.matches(rawPassword, encodePasswd);
	System.out.println("encodePasswd:" + encodePasswd);
	System.out.println(isMatch);
}
```

这样，我们的数据库中可以存储加密后的密码，这样，就避免了明文存储的风险。

在初始化用户时，我们把加密后的密码存储进数据库：

```
INSERT INTO user (id, username, password, name, age) VALUES (1, 'waylau', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '老卫', 30);
INSERT INTO user (id, username, password, name, age)  VALUES (2, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'Way Lau', 29);

```
