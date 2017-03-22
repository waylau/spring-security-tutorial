# 基于 JWT 的认证

我们在 `remember-me-hash`上，基于 Form 表单的方式，来实现基于散列的令牌方法的  Remember-Me 认证，我们新建一个 `jwt-authentication`项目。

## build.gradle
 
 修改 build.gradle 文件，让我们的`remember-me-hash`项目成为一个新的项目。

修改内容也比较简单，修改项目名称及版本即可。

```groovy
jar {
	baseName = 'jwt-authentication'
	version = '1.0.0'
}
```

添加 JWT 依赖：

```groovy
// 添加  JSON Web Token Support For The JVM 依赖
compile('io.jsonwebtoken:jjwt:0.7.0')
```

##  JWT 的认证

JWT 是 Json Web Token 的缩写。它是基于 [RFC 7519](https://tools.ietf.org/html/rfc7519) 标准定义的一种可以安全传输的 小巧 和 自包含 的 JSON 对象。由于数据是使用数字签名的，所以是可信任的和安全的。JWT 可以使用 HMAC 算法对密码进行加密或者使用RSA的公钥私钥对来进行签名。
