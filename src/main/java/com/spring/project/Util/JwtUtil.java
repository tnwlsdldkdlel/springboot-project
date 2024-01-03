package com.spring.project.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {
	private static String secretKey = "javapp1234dlrjtdmstlzmfltzldlekdl&&rmflrhdlrjtdms256qlxmdltkddmlanswkdufdmfrkwlrhdlTdjdiehlsek";

	// 토큰발행
	public static String getToken(String key, Object value) {
		Date expTime = new Date();
		expTime.setTime(expTime.getTime() + 1000 * 60 * 60 * 24); // 하루
		byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
		SecretKeySpec signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

		Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("type", "JWT");
		headerMap.put("alg", "HS256");

		Map<String, Object> map = new HashMap<>();
		map.put(key, value);

		JwtBuilder builder = Jwts.builder().setHeader(headerMap).setClaims(map).setExpiration(expTime).signWith(signKey,
				SignatureAlgorithm.HS256);
		return ((JwtBuilder) builder).compact();
	}

	// 토큰 디코딩
	public static String getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
				.parseClaimsJws(token).getBody();

		return claims.get("id").toString();
	}
}
