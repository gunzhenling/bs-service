package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Data;

/**
 * 账单比对批次
 * 
 * @author fanhang
 */
@Data
public class BillCompareBatch {

	private Integer id;
	private String appId;
	private String name;
	private String payType;
	private String billDate;

	/**
	 * 账目出错数
	 */
	private Integer mistakeCount;

	/**
	 * 待处理账目出错数
	 */
	private Integer unhandleMistakeCount;

	/**
	 * 账单支付数
	 */
	private Integer billPayCount;

	/**
	 * 请求支付数目
	 */
	private Integer requestPayCount;

	/**
	 * 账单退款数
	 */
	private Integer billRefundCount;

	/**
	 * 请求退款数目
	 */
	private Integer requestRefundCount;

	/**
	 * 账单支付金额
	 */
	private Integer billPayAmount;

	/**
	 * 请求支付金额
	 */
	private Integer requestPayAmount;

	/**
	 * 账单退款金额
	 */
	private Integer billRefundAmount;

	/**
	 * 请求退款金额
	 */
	private Integer requestRefundAmount;

	/**
	 * 支付服务费
	 */
	private Integer payServiceCharge;

	/**
	 * 退款服务费
	 */
	private Integer refundServiceCharge;

	private Date createTime;
	
	private Date updateTime;
}
