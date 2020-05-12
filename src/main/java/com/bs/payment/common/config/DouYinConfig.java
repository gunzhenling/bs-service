package com.bs.payment.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.ToString;

/**
 * 字节跳动配置
 * @author zhenling
 *
 */
@Data
@ToString
@Configuration
@ConfigurationProperties(prefix="douyin")
public class DouYinConfig {
//	支付应用id
	private String appid;
//	支付商户
	private String mchid;
//	支付秘钥
	private String sercet;
//	验签公钥
	private String publicKey;
//	接口请求地址
	private String url;

}
