package com.zachary.springboot.blog.pushlian.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * 
 * @author zachary
 * @desc jwt安全验证
 *
 */
public class JwtUtil {
	public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	public static final String EXPIRED_CODE = "FS0001";
	public static final String SIGNATURE_CODE = "FS0002";
	private final String stringKey = "zachary";

	public static JwtUtil getInstance() {
		return JwtHolder.JwtUtil;
	}

	private static class JwtHolder {
		private static JwtUtil JwtUtil = new JwtUtil();
	}

	private SecretKey generalKey() {

		byte[] encodedKey = Base64.decodeBase64(stringKey);

		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

		return key;
	}

	private String createJWT(String id, String issuer, String subject, Map<String, Object> claims, long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		SecretKey key = generalKey();

		JwtBuilder builder = Jwts.builder().setClaims(claims).setId(id).setIssuedAt(now).setIssuer(issuer)
				.setSubject(subject).signWith(signatureAlgorithm, key);
		if (ttlMillis >= 0L) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	private Claims parseJWT(String jwt) {
		SecretKey key = generalKey();

		Claims claims = (Claims) Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
		return claims;
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	public ResultCode<String> createJWT(Jwt jwt) {
		if (jwt == null) {
			return ResultCode.getFailure(null, "jwt it's not null!");
		}
		String subject = jwt.getSubject();
		if (StringUtils.isBlank(subject)) {
			return ResultCode.getFailure(null, "Subject it's not null!");
		}
		String issuer = jwt.getIssuer();
		if (StringUtils.isBlank(issuer)) {
			return ResultCode.getFailure(null, "issuer it's not null!");
		}
		String jwtId = StringUtils.isBlank(jwt.getJwtId()) ? "x11111" : jwt.getJwtId();

		Long ttlMillis = Long.valueOf(jwt.getTtlMillis() == null ? 7200000L : jwt.getTtlMillis().longValue());

		
		Map<String, Object> claims = BeanUtils.isEmptyContainer(jwt.getClaims()) ? new HashMap() : jwt.getClaims();
		String jwtStr = "";
		try {
			jwtStr = createJWT(jwtId, issuer, subject, claims, ttlMillis.longValue());
		} catch (Exception e) {
			logger.error("jwt构建异常", e);
			return ResultCode.getFailure(null, "jwt构建异常");
		}
		return ResultCode.getSuccessReturn(null, "jwt构建异常", jwtStr);
	}

	public ResultCode<Claims> parseJWT(String jwt, String openId) {
		if (StringUtils.isBlank(jwt)) {
			return ResultCode.getFailure(null, "jwt it's not null!");
		}
		Claims claims = null;
		try {
			claims = parseJWT(jwt);
			if ((!StringUtils.isBlank(openId)) && (!StringUtils.equals(openId, claims.get("iss") + ""))) {
				return ResultCode.getFailure(null, "关键业务主键为空");
			}
		} catch (ExpiredJwtException e) {
			logger.error("jwttoken过期:{}", e);
			return ResultCode.getFailure("FS0001", "jwttoken过期");
		} catch (SignatureException e) {
			logger.error("jwt签名异常", e);
			return ResultCode.getFailure("FS0002", "jwt签名异常");
		} catch (Exception e) {
			logger.error("jwt系统异常", e);
			return ResultCode.getFailure(null, "jwt系统异常");
		}
		return ResultCode.getSuccessReturn(null, "success", claims);
	}

	public static class Jwt {
		private String jwtId;
		private String subject;
		private String issuer;
		private Long ttlMillis;
		private Map<String, Object> claims;

		public String getJwtId() {
			return this.jwtId;
		}

		public void setJwtId(String jwtId) {
			this.jwtId = jwtId;
		}

		public String getSubject() {
			return this.subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getIssuer() {
			return this.issuer;
		}

		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}

		public Long getTtlMillis() {
			return this.ttlMillis;
		}

		public void setTtlMillis(Long ttlMillis) {
			this.ttlMillis = ttlMillis;
		}

		public Map<String, Object> getClaims() {
			return this.claims;
		}

		public void setClaims(Map<String, Object> claims) {
			this.claims = claims;
		}
	}

	/*
	 * public static void main(String[] args) { String accessToken =
	 * "cwrcwc2f232131312r2d2"; Map<String, Object> ccee = new HashMap();
	 * ccee.put("key1", "bbs"); ccee.put("key2", "vvvvv"); ccee.put("openid",
	 * "accessToken"); try { Jwt jwt = new Jwt(); jwt.setSubject("{\"openid\":\"" +
	 * accessToken + "\"}"); jwt.setIssuer(accessToken);
	 * jwt.setTtlMillis(Long.valueOf(10L)); ResultCode<String> jwtCode =
	 * getInstance().createJWT(jwt);
	 * 
	 * String jwtId = (String) jwtCode.getRetval(); System.out.println(jwtId);
	 * 
	 * ResultCode<Claims> c = getInstance().parseJWT(jwtId, null);
	 * System.out.println(JSONObject.toJSONString(c)); } catch (Exception e) {
	 * e.printStackTrace(); logger.error("��������", e); } }
	 */
}
