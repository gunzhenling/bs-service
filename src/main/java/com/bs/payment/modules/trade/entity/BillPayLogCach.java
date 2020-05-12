package com.bs.payment.modules.trade.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 支付请求日志缓存
 * 
 * @author zhenling
 */
@Getter
@Setter
@ToString
public class BillPayLogCach {

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
