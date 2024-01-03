package com.spring.project.Util;

import java.util.Date;

public class Util {
	
	/*
	 * string to int
	 * */
	public static int stringToInt(String value) {
		return Integer.valueOf(value);
	}
	
	/*
	 * get createdAt
	 * */
	public static int createdAt() {
		return (int) (new Date().getTime()/1000);
	}

}
