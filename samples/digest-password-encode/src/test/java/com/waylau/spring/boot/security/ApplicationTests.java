package com.waylau.spring.boot.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	/**
	 * 摘要认证加密算法
	 */
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
}
