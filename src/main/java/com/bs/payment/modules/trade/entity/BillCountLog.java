package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Data;

/**
 * 账单汇总记录
 * 
 * @author fanhang
 */
@Data
public class BillCountLog {

	private Integer id;
	private String appId;
	private String merchantId;
	private String payType;
	private String billDate;
	/** 支付笔数 */
	private Integer payCount;
	/** 退款笔数 */
	private Integer refundCount;
	/** 支付累计金额 */
	private Integer payTotalAmount;
	/** 退款累计金额 */
	private Integer refundTotalAmount;
	/** 服务费 */
	private Integer serviceCharge;
	/**
	 * 收入
	 */
	private Integer income;
	private Object details;
	private Date createTime;
}
