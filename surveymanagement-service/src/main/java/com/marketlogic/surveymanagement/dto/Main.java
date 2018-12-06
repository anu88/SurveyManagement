package com.marketlogic.surveymanagement.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		List<Something> list = Arrays.asList(
	            new Something("key1", "value1"),
	            new Something("key2", "value2"),
	            new Something("key3", "value3a"),
	            new Something("key3", "value3b"));

	        Map<String, String> map = list.stream().collect(Collectors.toMap(o -> o.key, o -> o.value));
	        System.out.println(map);
	    }

	    private static class Something {
	        final String key, value;

	        Something(final String key, final String value){
	            this.key = key;
	            this.value = value;
	        }
	    }
}
