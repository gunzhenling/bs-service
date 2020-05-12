package com.bs.payment.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 支付宝配置
 * 
 * @author fanhang
 */
@Slf4j
@Data
@ToString(exclude = {"alipayClient"})
public class AlipayConfig {
	/**
	 * 付款回调 URL: {endpoint}/trade/payment/alipay/{appId}/payNotify
	 */
	private static final String PAY_NOTIFY_URL = "%s/trade/payment/%s/alipay/payNotify";
	/**
	 * 退款回调 URL: {endpoint}/trade/payment/{appId}/alipay/refundNotify
	 */
	private static final String REFUND_NOTIFY_URL = "%s/trade/payment/%s/alipay/refundNotify";
	
	/**
	 * 支付宝服务接口网关
	 */
	private static final String SERVER_URL = "https://openapi.alipay.com/gateway.do";
	
	@JsonIgnore
	private String id;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 商户号(卖家 id)
	 */
	private String mchid;
	/**
	 * 支付宝 appId
	 */
	private String appid;
	/**
	 * 加密类型("RSA" or "RSA2": sha256WithRsa)
	 */
	private String signType = AlipayConstants.SIGN_TYPE_RSA;
	/**
	 * 环境标识, 支付宝使用单一账号, 以此区分 dev/test/prod 各环境数据
	 */
	private String environment;
	
	/**
	 * 支付宝 privateKey
	 */
	private String privateKey;
	/**
	 * 支付宝 publicKey
	 */
	private String publicKey;
	/**
	 * 支付宝付款回调
	 */
	private String payNotifyUrl;
	/**
	 * 支付宝退款回调
	 */
	private String refundNotifyUrl;
	
	private AlipayClient alipayClient;
	
	/**
	 * @see AlipayConfig#mchid
	 * @return
	 */
	public String getSellerId() {
		return this.mchid;
	}
	
	public AlipayClient getAlipayClient() {
		return alipayClient;
	}

	public void init(String endpoint) throws Exception {
		Assert.hasLength(this.appid, "[appid] must not be Null");
		Assert.isTrue(StringUtils.containsAny(this.signType, AlipayConstants.SIGN_TYPE_RSA, AlipayConstants.SIGN_TYPE_RSA2), "unsupported signType: " + this.signType);
		Assert.hasLength(this.privateKey, "[privateKey] must not be Null");
		Assert.hasLength(this.publicKey, "[publicKey] must not be Null");
		
		// 回调地址
		this.setPayNotifyUrl(String.format(PAY_NOTIFY_URL, endpoint, this.id));
		this.setRefundNotifyUrl(String.format(REFUND_NOTIFY_URL, endpoint, this.id));
		
		this.alipayClient =  DefaultAlipayClient.builder(SERVER_URL, appid, privateKey)
				.format(AlipayConstants.FORMAT_JSON)
				.charset(AlipayConstants.CHARSET_UTF8)
				.alipayPublicKey(this.getPublicKey())
				.signType(this.getSignType())
				.build();
		log.info("appid [{}] alipay initialized: {}", this.id, this);
	}
}
