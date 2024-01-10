package com.spring.project.repository;

import com.spring.project.dto.BlogInfoDto;

public interface BlogRepository {

	public BlogInfoDto info(String token);
	
	public BlogInfoDto create(String token);
	
	public void update(BlogInfoDto blogInfoDto) throws Exception;
}
