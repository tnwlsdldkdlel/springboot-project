package com.spring.project.entity;

import java.util.ArrayList;
import java.util.List;

import com.spring.project.Util.Util;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	private String profile;

	private List<String> category = new ArrayList<>();

	private int createdAt;

	private int updatedAt;

	@OneToOne
	@JoinColumn(name = "seq_user")
	private User user;

	public static Blog create(User user) {
		Blog blog = new Blog();
		blog.setCreatedAt(Util.createdAt());
		blog.setUpdatedAt(Util.createdAt());
		blog.createUser(user);

		return blog;
	}
	
	public void createUser(User user) {
		this.user = user;
	}
}
