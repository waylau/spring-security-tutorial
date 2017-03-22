package com.waylau.spring.boot.security.util;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.waylau.spring.boot.security.controlller.util.StringUtil;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class StringUtilTest {

	@Test
	public void testConvertToList() {
		
		String raw = "sfasa;sdf";
 
		List<String> encode = StringUtil.convertToList(raw);
 
		System.out.println("encodePasswd:" + encode);
 
	}

}
