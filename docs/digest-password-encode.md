# 摘要认证的密码加密

在之前的案例中，我们的密码都是以明文形式存储在数据库中，明文存储给系统安全带来了极大的风险。本节将演示在摘要认证中，实现对密码进行加密存储。

 在 `digest-authentication`项目的基础上，我们构建了一个`digest-password-encode`项目。


## build.gradle
 
 修改 build.gradle 文件，让我们的`digest-password-encode`项目成为一个新的项目。

修改内容也比较简单，修改项目名称及版本即可。

```groovy
jar {
	baseName = 'digest-password-encode'
	version = '1.0.0'
}
```

## 密码加密算法

Sprint Security 所使用的密码加密算法格式为 `HEX( MD5(username:realm:password) )`所以，当我们使用账号 waylau 密码 123456 时，生成的密码如下：

```
waylau:spring security tutorial:123456  -> b7ace5658b44f7295e7e8e36da421502
```

具体生成的密码的代码可以看 ApplicationTests.java:

```java
@Test
public void testGenerateDigestEncodePassword() {
	String username = "waylau";
	String realm = "spring security tutorial";
	String password = "123456";

	String a1Md5 = this.encodePasswordInA1Format(username, realm, password);
	
	System.out.println("a1Md5:" + a1Md5);
}

private String encodePasswordInA1Format(String username, String realm, String password) {
	String a1 = username + ":" + realm + ":" + password;

	return md5Hex(a1);
}

private String md5Hex(String data) {
	MessageDigest digest;
	try {
		digest = MessageDigest.getInstance("MD5");
	}
	catch (NoSuchAlgorithmException e) {
		throw new IllegalStateException("No MD5 algorithm available!");
	}

	return new String(Hex.encode(digest.digest(data.getBytes())));
}
```


这样，我们的数据库中可以存储加密后的密码，这样，就避免了明文存储的风险。



## 如果启用密码加密机制

DigestAuthenticationFilter.passwordAlreadyEncoded 设置为 true 即可：

```java
@Bean
public DigestAuthenticationFilter digestAuthenticationFilter(
		DigestAuthenticationEntryPoint digestAuthenticationEntryPoint) throws Exception {

	DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
	digestAuthenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint);
	digestAuthenticationFilter.setUserDetailsService(userDetailsService);
	digestAuthenticationFilter.setPasswordAlreadyEncoded(true); // 密码已经加密
	return digestAuthenticationFilter;
}
```

## 限制

除默认的加密方式外，不能在摘要认证上采用其他的密码加密方式。