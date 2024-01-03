package com.spring.project.dto;

import java.util.ArrayList;
import java.util.List;

import com.spring.project.entity.Blog;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BlogInfoDto {

	private Long seq;
	private Long seq_user;
	private String profile;
	private List<String> category = new ArrayList<>();
	private int updatedAt;
	private String title;

	public BlogInfoDto(Blog blog) {
		this.seq = blog.getSeq();
		this.seq_user = blog.getUser().getSeq();
		this.profile = blog.getProfile();
		this.category = blog.getCategory();
		this.updatedAt = blog.getUpdatedAt();
		this.title = blog.getTitle();
	}

}
