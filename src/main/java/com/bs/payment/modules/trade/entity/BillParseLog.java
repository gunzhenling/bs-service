package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Data;

/**
 * 账单解析日志
 * 
 * @author fanhang
 */
@Data
public class BillParseLog {

	private Integer id;
	private String appId;
	private String payType;
	private String billDate;
	private Date createTime;
	private String remark;
}
