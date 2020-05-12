package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 退款请求日志
 * 
 * @author fanhang
 */
@Getter
@Setter
@ToString
public class RefundRequestLog {

	private Integer id;
	private String appId;
	private String payType;

	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 退款单号
	 */
	private String refundNo;
	/**
	 * 总金额
	 */
	private Integer totalAmount;
	/**
	 * 退款金额
	 */
	private Integer refundAmount;

	private String clientIp;
	private String extra;
	private Date createTime;

}
