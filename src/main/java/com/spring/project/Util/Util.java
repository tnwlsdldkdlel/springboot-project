package com.spring.project.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
	
	/*
	 * get today
	 * 
	 */
	public static String today() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formatedNow = now.format(formatter);
		
		return formatedNow;
	}
	
	/*
	 * get time
	 * 
	 */
	public static String time() {
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
		String formatedTime = now.format(formatter);
		
		return formatedTime;
	}
	
	/*
	 * set sha256
	 * */
	public static String sha256(String value) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(value.getBytes()); 
		
		return bytesToHex(messageDigest.digest()); 
	}
	
	public static String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		
        for (byte b : bytes) {
            builder.append(String.format("%02x", b)); 
        }
       
        return builder.toString(); 
	}
}
