package com.bs.payment.modules.trade.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 账单退款记录缓存
 * 
 * @author zhenling
 */
@Getter
@Setter
@ToString
public class BillRefundLogCach {

	private Integer id;
	private String appId;
	private String payType;
	private String billDate;
	private String orderNo;
	private String refundNo;
	private String tradeNo;
	private Integer refundAmount;
	private Integer serviceCharge;
	private Date tradeTime;
	@JsonIgnore
	private Object details;
	private Date createTime;

}
