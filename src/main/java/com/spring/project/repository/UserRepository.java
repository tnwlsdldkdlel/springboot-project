package com.spring.project.repository;

import com.spring.project.dto.UserInfoDto;
import com.spring.project.dto.UserJoinDto;

public interface UserRepository {
	
	public UserInfoDto join(UserJoinDto userJoinDto);
	
	public UserInfoDto info(String token);
	
	public UserInfoDto login(UserInfoDto userInfoDto);

}
