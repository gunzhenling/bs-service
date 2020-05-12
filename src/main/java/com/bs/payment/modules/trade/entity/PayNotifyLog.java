package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 支付回调日志
 * 
 * @author fanhang
 */
@Getter
@Setter
@ToString
public class PayNotifyLog {

	private Integer id;
	private String appId;
	private String payType;
	private String orderNo;
	private String tradeNo;
	private String feeType;
	private Integer cashAmount;
	private Integer totalAmount;
	private String extra;
	private Date tradeTime;
	private Date createTime;
	private Object details;
}
