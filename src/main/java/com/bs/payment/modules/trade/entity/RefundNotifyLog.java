package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Data;

/**
 * 退款回调日志记录
 * 
 * @author fanhang
 */
@Data
public class RefundNotifyLog {
	private Integer id;
	private String appId;
	private String payType;
	private String orderNo;
	private String refundNo;
	private String tradeNo;
	private Integer refundAmount;
	private Integer totalAmount;
	private String refundStatus;
	private String extra;
	private Date tradeTime;
	private Date createTime;
	private Object details;
}
