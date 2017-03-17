# 集成测试
 
我们从 Hello World 项目入手，增加单元测试功能。

我们新家了一个名为`hello-world-test`的 Gradle 项目。
 

## 环境

* Gradle 3.4.1
* Spring Boot 1.5.2.RELEASE 
* Thymeleaf 3.0.3.RELEASE
* Thymeleaf Layout Dialect 2.2.0
* Spring Security Test 4.2.2.RELEASE  
 
## build.gradle
 
### 1. 修改项目的名称

修改 build.gradle 文件，让我们的`hello-world`项目成为一个新的项目。

修改内容也比较简单，修改项目名称及版本即可。

```groovy
jar {
	baseName = 'hello-world-test'
	version = '1.0.0'
}
```

### 2. 指定依赖

```
// 依赖关系
dependencies {
    ......
	// 该依赖对于编译测试是必须的，默认包含编译产品依赖和编译时依
	testCompile('org.springframework.security:spring-security-test:4.2.2.RELEASE')
	......
}
```

## 编写测试类

### 测试 `/users`

首先我们测试 `/users`接口，编写UserControllerTest.java ：

```
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
    @Test
    public void testList() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.get("/users"))
    		.andExpect(status().isOk());
    }
}
```

运行 JUnit，结果是测试没有通过。在控制台，我们能看到如下请求和相应的结果：

```

MockHttpServletRequest:
      HTTP Method = GET
      Request URI = /users
       Parameters = {}
          Headers = {}

Handler:
             Type = null

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 302
    Error message = null
          Headers = {X-Content-Type-Options=[nosniff], X-XSS-Protection=[1; mode=block], Cache-Control=[no-cache, no-store, max-age=0, must-revalidate], Pragma=[no-cache], Expires=[0], X-Frame-Options=[DENY], Location=[http://localhost/login]}
     Content type = null
             Body = 
    Forwarded URL = null
   Redirected URL = http://localhost/login
          Cookies = []
```

可以看到响应的状态码为 302，重定向到了 `http://localhost/login` URL。


我们再次修改测试接口，这一次我们使用 mock 用户名为“waylau”角色权限为“USER”的用户：

```
@Test
@WithMockUser(username="waylau", password="123456", roles={"USER"})  // mock 了一个用户
public void testListWithUser() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.get("/users"))
		.andExpect(status().isOk());
}
```

这次，我们的测试用例通过。如果我们把用的角色权限改为“ADMIN”，会怎么样呢？

```
MockHttpServletRequest:
      HTTP Method = GET
      Request URI = /users
       Parameters = {}
          Headers = {}

Handler:
             Type = null

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 403
    Error message = Access is denied
          Headers = {X-Content-Type-Options=[nosniff], X-XSS-Protection=[1; mode=block], Cache-Control=[no-cache, no-store, max-age=0, must-revalidate], Pragma=[no-cache], Expires=[0], X-Frame-Options=[DENY]}
     Content type = null
             Body = 
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
```

看到测试失败后，控制台打印的信息，状态码为 403，错误信息为 “Access is denied”。