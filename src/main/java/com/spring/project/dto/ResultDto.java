package com.spring.project.dto;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultDto {

	private String result; // success , fail
	private String message;
	private Map<String, Object> data;
}
