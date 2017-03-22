/**
 * 
 */
package com.waylau.spring.boot.security.controlller.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 *
 */
public class StringUtil {
	private static final String SPLIT_REGEX = ";";  
	
	public static List<String> convertToList(String string) {
		List<String> result = new ArrayList<>();
		if (string ==null || string.isEmpty() || string.length() == 0) {
			return result;
		}
		result = Arrays.asList(string.split(SPLIT_REGEX));  
		return result;
	}
	
	public static Set<String> convertToSet(String string) {
		Set<String> result = new HashSet<String>(convertToList(string));
		return result;
	}
}
