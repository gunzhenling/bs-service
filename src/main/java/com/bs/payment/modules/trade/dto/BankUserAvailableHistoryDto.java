package com.bs.payment.modules.trade.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 
 * 
 * 描述： 
 * 
 * <pre>
 * 用户余额变更表
 * </pre>
 * 
 * @author zhenling 
 */
@Data
public class BankUserAvailableHistoryDto {

	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 变更标识 0增加1减少
	 */
	private Integer isReduce;
	/**
	 * 变更标识描述 0增加1减少
	 */
	private String isReduceDesc;
	/**
	 * 变更类型，1支付，2提现
	 */
	private Integer changeType; //变更类型
	/**
	 * 变更类型，1支付，2提现
	 */
	private String changeTypeDesc; //变更类型
	/**
	 * 变更说明
	 */
	private String description;
	/**
	 * 变更后的可用金额
	 */
	private BigDecimal availableMoney;
	/**
	 * 变更金额
	 */
	private BigDecimal changeMoney;
	/**
	 * 变更号，订单号或提现单号
	 */
	private String changeNo;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
