package com.bs.payment.modules.trade.vo;

import java.util.Date;

import lombok.Data;

/**
 * 支付订单查询响应
 * 
 * @author fanhang
 */
@Data
public class PayQueryResponseVO {
	/**
	 * 返回明细
	 */
	private final Object detail;
	
	private String orderNo;
	private String tradeNo;
	/**
	 * 交易是否成功
	 */
	private final String tradeStatus;
	
	private String feeType;
	
	private Integer cashAmount;
	
	private Integer totalAmount;
	
	private Date tradeTime;
	
	public PayQueryResponseVO(Object detail, String tradeStatus) {
		this.detail = detail;
		this.tradeStatus = tradeStatus;
	}
}
