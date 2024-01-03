package com.spring.project.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.project.dto.BlogInfoDto;
import com.spring.project.entity.Blog;
import com.spring.project.entity.QBlog;
import com.spring.project.entity.QUser;
import com.spring.project.entity.User;

import jakarta.persistence.EntityManager;

@Repository
@Transactional(readOnly = true)
public class BlogRepositoryImpl implements BlogRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public BlogRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	@Transactional
	public BlogInfoDto info(String token) {
		QBlog qBlog = QBlog.blog;
		QUser qUser = QUser.user;

		BlogInfoDto blogInfoDto = queryFactory
				.select(Projections.bean(BlogInfoDto.class, qBlog.seq, qBlog.category, qBlog.profile))
				.from(qBlog)
				.where(qBlog.user.id.eq(token))
				.fetchOne();
		
		if(blogInfoDto == null) {
			User user = queryFactory
				.select(qUser)
				.where(qUser.id.eq(token))
				.fetchOne();
			Blog blog = Blog.create(user);
			
			return blogInfoDto = new BlogInfoDto(blog);
		} else {
			return blogInfoDto;
		}
	}
}
