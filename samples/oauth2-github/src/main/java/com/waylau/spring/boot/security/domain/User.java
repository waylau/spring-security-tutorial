package com.waylau.spring.boot.security.domain;

/**
 * User 实体
 * 
 * @since 1.0.0 2017年3月5日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class User {
 
 
	private String name;
 
	private Integer age;
 
	public User(String name, Integer age) {
		this.name = name;
		this.age = age;
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
	public String toString() {
		return String.format("User[name='%s', age='%d' ]", name, age);
	}
}
