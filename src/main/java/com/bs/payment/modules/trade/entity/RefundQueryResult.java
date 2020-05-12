package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Data;

/**
 * 退款查询结果
 * 
 * @author fanhang
 */
@Data
public class RefundQueryResult {
	private Integer id;
	private String appId;
	private String payType;
	private String orderNo;
	private String refundNo;
	private String tradeNo;
	private Integer refundAmount;
	private Integer totalAmount;
	private String extra;
	private Date createTime;
	private Object details;
}
