package com.spring.project.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.project.dto.UserInfoDto;
import com.spring.project.dto.UserJoinDto;
import com.spring.project.entity.QUser;
import com.spring.project.entity.User;

import jakarta.persistence.EntityManager;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public UserRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	@Transactional
	public UserInfoDto join(UserJoinDto userJoinDto) {
		QUser quser = QUser.user;
		
		// 중복 id 확인
		Long idCheck = queryFactory
			.select(quser.seq)
			.from(quser)
			.where(quser.id.eq(userJoinDto.getId()))
			.fetchCount();
		
		if(idCheck > 1) {
			return null;
		} else {
			User user = User.create(userJoinDto);
			em.persist(user);

			return new UserInfoDto(user);
		}
	}

	@Override
	public UserInfoDto info(String userId) {
		QUser user = QUser.user;
		
		return queryFactory
			.select(Projections.bean(UserInfoDto.class, user.seq, user.name, user.id))
			.from(user)
			.where(user.id.eq(userId))
			.fetchOne();
	}

	@Override
	public UserInfoDto login(UserInfoDto userInfoDto) {
		QUser user = QUser.user;
		
		UserInfoDto findUser = 
			queryFactory
				.select(Projections.bean(UserInfoDto.class, user.seq, user.name, user.id, user.password))
				.from(user)
				.where(
						user.id.eq(userInfoDto.getId()),
						user.password.eq(userInfoDto.getPassword())
						)
				.fetchOne();
		
		if(findUser == null) {
			return null;
		} else {
			return findUser;
		}
	}
}
