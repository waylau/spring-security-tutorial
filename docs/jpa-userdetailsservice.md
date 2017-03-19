# 使用 JPA 及 UserDetailsService

本文展示了如何使用 JPA 自定义 UserDetailsService 及数据库 的方式来进行认证。在本例，我们将认证信息存储于 H2 数据库中。

在 `ldap-authentication`项目的基础上，我们构建了一个`jpa-userdetailsservice`项目。


## build.gradle
 
 修改 build.gradle 文件，让我们的`jpa-userdetailsservice`项目成为一个新的项目。

修改内容也比较简单，修改项目名称及版本即可。

```groovy
jar {
	baseName = 'jpa-userdetailsservice'
	version = '1.0.0'
}
```
 
 

我们将 `spring-boot-starter-jdbc`依赖改为了 `spring-boot-starter-data-jpa`

## 实体

### 修改  User

修改 User，增加了 username、password 两个字段，用于登录时使用。authorities 字段，用于存储该用户的权限信息。同时，User 也实现了 `org.springframework.security.core.userdetails.UserDetails` 接口。其中 User 与 Authority是多对多的关系。


```java
@Entity  // 实体
public class User implements UserDetails, Serializable{
 
	private static final long serialVersionUID = 1L;
	
	@Id  // 主键
    @GeneratedValue(strategy=GenerationType.IDENTITY) // 自增长策略
	private Long id; // 用户的唯一标识

	@Column(nullable = false, length = 50) // 映射为字段，值不能为空
 	private String name;
	
	@Column(nullable = false)
	private Integer age;

    @Column(nullable = false, length = 50, unique = true)  
    private String username; // 用户账号，用户登录时的唯一标识
 
    @Column(length = 100)
    private String password; // 登录时密码
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;
    
	protected User() {  // JPA 的规范要求无参构造函数；设为 protected 防止直接使用 
	}

	public User(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
    
	@Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
    public String toString() {
        return String.format(
                "User[id=%d, username='%s', name='%s', age='%d', password='%s']",
                id, username, name, age, password);
    }
}
```


### 新增  Authority

新增  Authority 用于存储权限信息。 Authority 实现了 GrantedAuthority 接口，并重写了其 getAuthority 方法。
```java
@Entity  // 实体
public class Authority implements GrantedAuthority {
 
	private static final long serialVersionUID = 1L;

	@Id  // 主键
    @GeneratedValue(strategy=GenerationType.IDENTITY) // 自增长策略
	private Long id; // 用户的唯一标识

	@Column(nullable = false) // 映射为字段，值不能为空
 	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {
		return name;
	}
	 
	public void setName(String name) {
		this.name = name;
	}
}
```

## 新增存储库

新增`com.waylau.spring.boot.security.repository.UserRepository`，由于继承自了 `org.springframework.data.jpa.repository.JpaRepository`接口，所以大部分与数据库操作的接口，我们在UserRepository都无需定义了。我们只需要定义一个

```java
public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
}
```
`findByUsername`是用来根据用户账号来查询用户，这个在查询用户权限时要用到。

## 服务类

我们的服务类继承自我们定义的UserService，以及`org.springframework.security.core.userdetails.UserDetailsService`。其中，UserDetailsService是用来查询认证信息相关的接口，我们重写其`loadUserByUsername`方法。

```java
@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	......
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

}
```


## 修改配置类 SecurityConfig

在配置类中，我们把 UserDetailsService 的实现赋给  AuthenticationManagerBuilder：

```
@Autowired
private UserDetailsService userDetailsService;
......

/**
 * 认证信息管理
 * @param auth
 * @throws Exception
 */
@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService);
} 
```

## 初始化数据库

用于是使用了 JPA ，所以，默认表结构是会根据实体逆向生成的。

至于数据，我们在项目的 `src/main/resources` 目录下，放置一个 import.sql 脚本文件。里面是我们要初始化的数据，只要应用启动，就会自动把  import.sql 的数据给导入数据库。

以下为我们要初始化的数据：


```sql
INSERT INTO user (id, username, password, name, age) VALUES (1, 'waylau', '123456', '老卫', 30);
INSERT INTO user (id, username, password, name, age)  VALUES (2, 'admin', '123456', 'Way Lau', 29);

INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
```

 