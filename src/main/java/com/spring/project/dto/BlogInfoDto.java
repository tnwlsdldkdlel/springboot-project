package com.spring.project.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.spring.project.entity.Blog;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Component
@Slf4j
@Data
public class BlogInfoDto {
	private Long seq;
	private Long seqUser;
	private String profile;
	private List<String> category = new ArrayList<>();
	private int updatedAt;
	private String title;
	private MultipartFile uploadProfileImg;
	private String profileImg;

	private static String filePath;

	public BlogInfoDto(Blog blog) {
		this.seq = blog.getSeq();
		this.seqUser = blog.getUser().getSeq();
		this.profile = blog.getProfile();
		this.category = blog.getCategory();
		this.updatedAt = blog.getUpdatedAt();
		this.title = blog.getTitle();
		this.profileImg = filePath + blog.getProfileImg();
	}

	@Value("${spring.servlet.multipart.location}")
	public void setFilePath(String file_path) {
		BlogInfoDto.filePath = file_path;
	}
}
