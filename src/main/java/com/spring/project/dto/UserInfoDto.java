package com.spring.project.dto;

import com.spring.project.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto {

	private Long seq;
	private String name;
	private String id;
	private String token;
	private String password;
	
	public UserInfoDto(Long seq, String name, String id, String password) {
		this.seq = seq;
		this.name = name;
		this.id = id;
		this.password = password;
	}
	
	public UserInfoDto(User user) {
		this.seq = user.getSeq();
		this.name = user.getName();
		this.id = user.getId();
	}

}
