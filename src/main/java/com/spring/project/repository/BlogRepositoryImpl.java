package com.spring.project.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.project.Util.JwtUtil;
import com.spring.project.dto.BlogInfoDto;
import com.spring.project.entity.Blog;
import com.spring.project.entity.QBlog;
import com.spring.project.entity.QUser;
import com.spring.project.entity.User;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class BlogRepositoryImpl implements BlogRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public BlogRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	@Transactional
	public BlogInfoDto info(String userId) {
		QBlog qBlog = QBlog.blog;
		QUser qUser = QUser.user;
		
		BlogInfoDto blogInfoDto = queryFactory
				.select(Projections.bean(BlogInfoDto.class, qBlog.seq, qBlog.category, qBlog.profile, qBlog.title, qBlog.profileImg))
				.from(qBlog)
				.where(qBlog.user.id.eq(userId))
				.fetchOne();

		// blog 첫 개설인 경우
		if(blogInfoDto == null) {
			User user = queryFactory
				.select(qUser)
				.from(qUser)
				.where(qUser.id.eq(userId))
				.fetchOne();
			Blog blog = Blog.create(user);
			em.persist(blog);
			
			return blogInfoDto = new BlogInfoDto(blog);
		} else {
			return blogInfoDto;
		}
	}

	@Override
	public void updateBlogProfileImg(BlogInfoDto blogInfoDto) {
		String userId = new JwtUtil().getUserId(blogInfoDto.getToken());
		
		QBlog qBlog = QBlog.blog;
		BlogInfoDto find = em.find(blogInfoDto.getClass(), userId);
		
		log.info(find.toString());
	}
}
