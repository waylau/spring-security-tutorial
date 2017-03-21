# Remember-Me（记住我）认证：基于持久化的令牌方法
 

## 基于持久化的令牌方法

这种方法使用数据库来存储令牌信息。这种方法是基于 <http://jaspan.com/improved_persistent_login_cookie_best_practice> 文章所做出的小修改。使用这种方法可以提供一个数据源引用：
 
```sql
create table persistent_logins (username varchar(64) not null,
								series varchar(64) primary key,
								token varchar(64) not null,
								last_used timestamp not null)
```



## PersistentTokenBasedRememberMeServices

这个类可以使用相同的方式 TokenBasedRememberMeServices，但它还需要配置一个 PersistentTokenRepository 来存储令牌。有两个标准实现。

* InMemoryTokenRepositoryImpl ：仅用于测试。
* JdbcTokenRepositoryImpl ：存储令牌到数据库中。


