package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Data;

/**
 * 账单比对明细
 * 
 * @author fanhang
 */
@Data
public class BillCompareDetail {

	private Integer id;
	private Integer batchId;
	private String payType;
	private String operateType;
	private String orderNo;
	private String refundNo;
	private Integer billAmount;
	private Integer requestAmount;
	private Date tradeTime;
	private Integer compareStatus;
	private String compareDesc;
	private Date createTime;

}
