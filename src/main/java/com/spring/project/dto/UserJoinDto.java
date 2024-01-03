package com.spring.project.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserJoinDto {

	private String id;
	private String password;
	private String name;
	private String birth;
	private String cell;
	private String phone;
	private String token;

}
