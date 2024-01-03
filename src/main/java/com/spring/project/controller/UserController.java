package com.spring.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.project.Util.JwtUtil;
import com.spring.project.dto.ResultDto;
import com.spring.project.dto.UserInfoDto;
import com.spring.project.dto.UserJoinDto;
import com.spring.project.repository.UserRepositoryImpl;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
@Slf4j
public class UserController {

	private final UserRepositoryImpl userRepository;

	@PostMapping("/join")
	public ResponseEntity<ResultDto> join(UserJoinDto userJoinDto) {
		UserInfoDto userInfoDto = userRepository.join(userJoinDto);

		ResultDto resultDto = new ResultDto();

		if (userInfoDto == null) {
			resultDto.setResult("exist");
			resultDto.setMessage("이미 존재하는 회원정보");

			return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
		} else {
			String token = new JwtUtil().getToken("id", userInfoDto.getId());

			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("token", token);
			dataMap.put("userInfo", userInfoDto);

			resultDto.setResult("success");
			resultDto.setMessage("");
			resultDto.setData(dataMap);

			return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
		}
	}

	@GetMapping("/info/{token}")
	public ResponseEntity<ResultDto> info(@PathVariable("token") String token) {
		try {
			String userId = new JwtUtil().getUserId(token);
			UserInfoDto userInfoDto = userRepository.info(userId);
			
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("userInfo", userInfoDto);
			
			ResultDto resultDto = new ResultDto();
			resultDto.setResult("success");
			resultDto.setMessage("");
			resultDto.setData(dataMap);

			return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
		} catch (ExpiredJwtException e) {
			ResultDto resultDto = new ResultDto();
			resultDto.setResult("expiredToken");
			resultDto.setMessage("로그인 만료");

			return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ResultDto> login(UserInfoDto userInfoDto) {
		userInfoDto = userRepository.login(userInfoDto);
		ResultDto resultDto = new ResultDto();
	
		if (userInfoDto == null) {
			resultDto.setResult("fail");
			resultDto.setMessage("로그인 실패");

			return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
		} else {
			String token = new JwtUtil().getToken("id", userInfoDto.getId());

			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("token", token);
			dataMap.put("userInfo", userInfoDto);

			resultDto.setResult("success");
			resultDto.setMessage("");
			resultDto.setData(dataMap);

			return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
		}
	}
}
