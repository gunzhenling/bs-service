package com.bs.payment.modules.trade.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 转账日志记录
 * 
 * @author fanhang
 */
@Getter
@Setter
@ToString
public class TransferRequestLog {

	private Integer id;
	private String appId;
	private String payType;

	private String transferNo;
	private String account;
	private Integer amount;
	private String realName;
	private String remark;

	private String clientIp;
	private String extra;
	private Date createTime;

}
