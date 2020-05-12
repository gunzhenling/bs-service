package com.bs.payment.modules.trade.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 账单支付记录
 * 
 * @author fanhang
 */
@Data
public class BillPayLog {

	private Integer id;
	private String appId;
	private String payType;
	private String billDate;
	private String orderNo;
	private String tradeNo;
	private Integer payAmount;
	private Integer serviceCharge;
	private Date tradeTime;
	@JsonIgnore
	private Object details;
	private Date createTime;

}
