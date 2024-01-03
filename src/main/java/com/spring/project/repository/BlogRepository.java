package com.spring.project.repository;

import com.spring.project.dto.BlogInfoDto;

public interface BlogRepository {

	public BlogInfoDto info(String token);
}
