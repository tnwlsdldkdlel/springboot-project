package com.spring.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.project.dto.BlogInfoDto;
import com.spring.project.dto.ResultDto;
import com.spring.project.repository.BlogRepository;
import com.spring.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/blog")
public class BlogController {
	
	private final BlogRepository blogRepository;
	private final UserRepository userRepository;
	
	@GetMapping("/info/{token}")
	public ResponseEntity<ResultDto> info(@PathVariable("token") String token) {
		 BlogInfoDto blogInfoDto = blogRepository.info(token);
		 
		 Map<String, Object> dataMap = new HashMap<>();
		 dataMap.put("blogInfo", blogInfoDto);
		 
		 ResultDto resultDto = new ResultDto();
		 resultDto.setResult("success");
		 resultDto.setData(dataMap);
		 
		 return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
	}

}
