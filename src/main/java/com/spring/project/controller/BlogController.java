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
import com.spring.project.dto.BlogInfoDto;
import com.spring.project.dto.ResultDto;
import com.spring.project.repository.BlogRepository;
import com.spring.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/blog")
@Slf4j
public class BlogController {

	private final BlogRepository blogRepository;
	private final UserRepository userRepository;

	@GetMapping("/info/{token}")
	public ResponseEntity<ResultDto> info(@PathVariable("token") String token) {
		String userId = new JwtUtil().getUserId(token);
		BlogInfoDto blogInfoDto = blogRepository.info(userId);

		if (blogInfoDto == null) {
			blogInfoDto = blogRepository.create(userId);
		}

		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("blogInfo", blogInfoDto);

		ResultDto resultDto = new ResultDto();
		resultDto.setResult("success");
		resultDto.setData(dataMap);

		return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
	}

//	@PostMapping("/profile")
//	public ResponseEntity<ResultDto> profile(BlogInfoDto blogInfoDto) {
//		ResultDto resultDto = new ResultDto();
//
//		try {
//			blogRepository.updateBlogProfileImg(blogInfoDto);
//			resultDto.setResult("success");
//		} catch (Exception e) {
//			resultDto.setResult("fail");
//			resultDto.setMessage(e.getMessage());
//		}
//
//		return new ResponseEntity<ResultDto>(resultDto, HttpStatus.OK);
//	}

	@PostMapping("/info")
	public void info(BlogInfoDto blogInfoDto) {
		//ResultDto resultDto = new ResultDto();
		log.info(blogInfoDto.getProfileImg());
		
		String flag = blogInfoDto.getUploadProfileImg() == null ? "Y" : "N";
		log.info(flag);
//		try {
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
	}
}
