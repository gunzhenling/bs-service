package com.bs.payment.common.interceptor.jwt;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.BaseEncoding;

/**
 * 描述：
 * 
 * <pre>
 * - iss: jwt签发者,默认intbee-ucenter
   - aud: 应用ID
   - sub: 用户uuid
 * </pre>
 * 
 * @author qizai
 * @version: 0.0.1 2018年6月19日-下午6:57:28
 *
 */
public class JwtUtil {
	public static final String	HASH_ALGORITHM			= "SHA-256";
	public final static String	HEADER_BEARER			= "bearer";
	public final static String	HEADER_AUTHORIZATION	= "Authorization";
	public final static String	CLAIM_TYPE				= "type";
	public final static String	CLAIM_TYPE_JWT			= "JWT";
	public final static String	CLAIM_ROLE				= "role";
	public final static String	CLAIM_NAME				= "name";
	public final static String	CLAIM_USERID			= "userid";
	public final static String	CLAIM_APPID				= "appid";
	private static String		jwtSecret				= null;
	private static long			jwtExpiresSecond		= 604800;
	/** 迭代次数 */
	public static final int		HASH_INTERATIONS		= 1024;
	private static final int	SALT_SIZE				= 8;
	private static Logger		logger					= LoggerFactory.getLogger(JwtUtil.class);
	private static SecureRandom						random			= new SecureRandom();
	public static final Charset UTF_8 = StandardCharsets.UTF_8;

	public static final String genAccessKey() {
		String accessKey = UUID.randomUUID().toString().replaceAll("-", "");
		return accessKey;
	}

	public static final String genSecretKey() {
		return RandomStringUtils.randomAlphanumeric(32);
	}

	public static String genSalt() {
		Validate.isTrue(SALT_SIZE > 0, "SALT_SIZE must be a positive integer (1 or larger)", SALT_SIZE);
		byte[] salt = new byte[SALT_SIZE];
		random.nextBytes(salt);
		return BaseEncoding.base16().encode(salt);
	}

	/**
	 * @param jwtSecret
	 *            the {@link #jwtSecret} to set
	 */
	public void setJwtSecret(String jwtSecret) {
		JwtUtil.jwtSecret = jwtSecret;
	}

	/**
	 * @param jwtExpiresSecond
	 *            the {@link #jwtExpiresSecond} to set
	 */
	public static void setJwtExpiresSecond(long jwtExpiresSecond) {
		JwtUtil.jwtExpiresSecond = jwtExpiresSecond;
	}

	/**
	 * @return the {@link #jwtExpiresSecond}
	 */
	public static long getJwtExpiresSecond() {
		return jwtExpiresSecond;
	}

	/**
	 * @return the {@link #jwtSecret}
	 */
	public static String getJwtSecret() {
		return jwtSecret;
	}

	/**
	 * 转换提取token中的数据
	 * 
	 * @param jwt
	 * @return
	 */
	public static JwtDetail parse(String jwt) {
		return parse(jwt, getJwtSecret());
	}

	public static JwtDetail parse(String jwt, String secret) {
		JwtDetail jwtDetail = new JwtDetail();
		try {
			HmacKey key = new HmacKey(secret.getBytes(UTF_8));
			JwtConsumer jwtConsumer = new JwtConsumerBuilder()
					//有30s的一个有效空窗期，即允许到期30s内仍可以使用
					.setAllowedClockSkewInSeconds(30)
					.setRequireExpirationTime().setRequireSubject().setRequireJwtId()
					//
					.setSkipDefaultAudienceValidation()
					// .setExpectedSubject("cc")
					// .setExpectedIssuer("intbee-ucenter")
					// .setExpectedAudience("nesting")
					//
					.setVerificationKey(key).setRelaxVerificationKeyValidation().build();
			JwtClaims processedClaims = jwtConsumer.processToClaims(jwt);
			jwtDetail.setAppid(processedClaims.getStringClaimValue(CLAIM_APPID));
			jwtDetail.setUuid(processedClaims.getSubject());
			return jwtDetail;
		} catch (InvalidJwtException e) {
			if (e.hasExpired()) {
				jwtDetail.setExpired(e.hasExpired());
			} else {
				jwtDetail.setError(true);
			}
			logger.error("parse jwt error :" + e.getMessage(), e);
		} catch (Exception e) {
			jwtDetail.setError(true);
			logger.error("parse jwt error :" + e.getMessage(), e);
		}
		return jwtDetail;
	}

	/**
	 * 
	 * @param issuer
	 *            jwt签发者,默认intbee-ucenter
	 * @param appid
	 *            应用ID
	 * @param subject
	 *            用户uuid
	 * @param expireMinutes
	 * @return
	 */
	private static JwtClaims getJwtClaims(String issuer, String appid, String subject, long expireMinutes) {
		JwtClaims claims = new JwtClaims();
		claims.setIssuer(issuer);
		claims.setAudience(appid);
		claims.setSubject(subject);
		claims.setStringClaim(CLAIM_APPID, appid);
		claims.setExpirationTimeMinutesInTheFuture(expireMinutes);
		claims.setGeneratedJwtId();
		claims.setIssuedAtToNow();
		return claims;
	}

	/**
	 * 
	 * @param issuer
	 *            jwt签发者,默认intbee-ucenter
	 * @param appid
	 *            应用ID
	 * @param subject
	 *            用户uuid
	 * @param expireMinutes
	 * @param secret
	 * @return
	 * @throws JoseException
	 * @throws UnsupportedEncodingException
	 */
	public static String create(String issuer, String appid, String subject, long expireMinutes, String secret) {
		String idToken = null;
		try {
			HmacKey key = new HmacKey(secret.getBytes(UTF_8));
			JwtClaims claims = getJwtClaims(issuer, appid, subject, expireMinutes);
			JsonWebSignature jsonWebSignature = new JsonWebSignature();
			jsonWebSignature.setPayload(claims.toJson());
			jsonWebSignature.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
			jsonWebSignature.setKey(key);
			jsonWebSignature.setDoKeyValidation(false);

			idToken = jsonWebSignature.getCompactSerialization();
		} catch ( JoseException e) {
			logger.error("create jwt error:" + e.getMessage(), e);
		}
		return idToken;
	}

	/**
	 * 通过属性注入的形式使用jwtSecret和jwtExpiresSecond生成token
	 * @param issuer
	 * @param appid
	 * @param subject
	 * @return
	 */
	public static String create(String issuer, String appid, String subject) {
		long expireMinutes = getJwtExpiresSecond() / 60;
		return create(issuer, appid, subject, expireMinutes, getJwtSecret());
	}
}
