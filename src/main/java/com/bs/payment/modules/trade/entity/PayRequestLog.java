package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 支付请求日志
 * 
 * @author fanhang
 */
@Getter
@Setter
@ToString
public class PayRequestLog {

	private Integer id;
	private String appId;
	private String payType;
	private String channel;
	private String orderNo;
	private Integer amount;
	private String subject;
	private String signType;
	private String clientIp;
	private String extra;
	private Date createTime;
	private Object response;

}
