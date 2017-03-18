# 方法级别的安全
 
我们在`hello-world-test`的基础上，我们新建了一个名为`method-security`的  Gradle 项目。

本项目用于延时方法级别的安全设置。


## build.gradle
 
 修改 build.gradle 文件，让我们的`method-security`项目成为一个新的项目。

修改内容也比较简单，修改项目名称及版本即可。

```groovy
jar {
	baseName = 'method-security'
	version = '1.0.0'
}
```
 
