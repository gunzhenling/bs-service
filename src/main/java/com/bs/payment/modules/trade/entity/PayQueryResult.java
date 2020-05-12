package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Data;

/**
 * 支付查询结果
 * 
 * @author fanhang
 */
@Data
public class PayQueryResult {
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
