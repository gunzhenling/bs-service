package com.bs.payment.common.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {

	private String secret = "fa2jBYYXukgRft4J942F3TDCmMXqedcW";
	
	/**
	 * 受jwt保护的安全路径
	 */
	private List<String> securityPath;

}