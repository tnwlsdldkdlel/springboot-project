package com.spring.project.entity;

import com.spring.project.Util.Util;
import com.spring.project.dto.UserJoinDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	private String id;
	
	private String password;
	
	private String name;
	
	private int birth;
	
	private String cell;
	
	private int phone;
	
	private int createdAt;
	
	private int updatedAt;
	
	@OneToOne(mappedBy = "user")
	private Blog blog;
	
	public static User create(UserJoinDto dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setPassword(dto.getPassword());
		user.setName(dto.getName());
		user.setBirth(Util.stringToInt(dto.getBirth().replaceAll("-", "")));
		user.setCell(dto.getCell());
		user.setPhone(Util.stringToInt(dto.getPhone()));
		user.setCreatedAt(Util.createdAt());
		user.setUpdatedAt(Util.createdAt());
		
		return user;
	}
}
