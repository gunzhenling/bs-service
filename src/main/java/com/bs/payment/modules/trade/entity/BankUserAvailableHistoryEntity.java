package com.bs.payment.modules.trade.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("bs_bank_user_available_history")
public class BankUserAvailableHistoryEntity extends AbstractBaseEntity {

	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 变更标识 0增加1减少
	 */
	private Integer isReduce;
	/**
	 * 变更类型，1支付，2提现
	 */
	private Integer changeType; //变更类型
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

}
