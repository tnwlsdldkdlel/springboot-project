package com.spring.project.repository;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.project.Util.Util;
import com.spring.project.dto.BlogInfoDto;
import com.spring.project.entity.Blog;
import com.spring.project.entity.QBlog;
import com.spring.project.entity.QUser;
import com.spring.project.entity.User;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
@Slf4j
public class BlogRepositoryImpl implements BlogRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;
	
	@Value("${spring.servlet.multipart.location}")
	private String FILE_PATH;

	public BlogRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public BlogInfoDto info(String userId) {
		QBlog qBlog = QBlog.blog;
	
		BlogInfoDto blogInfoDto = queryFactory
				.select(Projections.bean(BlogInfoDto.class, qBlog.seq, qBlog.category, qBlog.profile, qBlog.title, qBlog.profileImg))
				.from(qBlog)
				.where(qBlog.user.id.eq(userId))
				.fetchOne();
		
		// blog 첫 개설인 경우
		return blogInfoDto;
	}
	
	@Transactional
	public BlogInfoDto create(String userId) {
		QBlog qBlog = QBlog.blog;
		QUser qUser = QUser.user;
		
		User user = queryFactory
				.select(qUser)
				.from(qUser)
				.where(qUser.id.eq(userId))
				.fetchOne();
		Blog blog = Blog.create(user);
		em.persist(blog);
			
		return new BlogInfoDto(blog);
	}

	@Override
	@Transactional
	public void updateBlogProfileImg(BlogInfoDto blogInfoDto) throws Exception {
		QBlog qBlog = QBlog.blog;
		Blog blog = em.find(Blog.class, blogInfoDto.getSeq());
		
		// 파일업로드 경로/20240109/이름+날짜(시간분초) 암호화
		String newFileName = Util.sha256(blogInfoDto.getUploadProfileImg().getOriginalFilename() + "_" + Util.time());
		
		// 오늘날짜로된 경로가 없을 경우
		File file = new File(FILE_PATH + "/" + Util.today());
		if(!file.exists()) {
			file.mkdir();
		}
		
		// img upload
		String path = "/" + Util.today() + "/" + newFileName + ".jpg";
		blogInfoDto.getUploadProfileImg().transferTo(new File(FILE_PATH + path));
		
		// db update
		blog.setProfileImg(path);
		blog.setUpdatedAt(Util.createdAt());
	}
}
