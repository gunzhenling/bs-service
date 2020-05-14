package com.bs.payment.modules.trade.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户余额表
 * 
 * @author gunzhenling
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("bs_bank_user")
public class BankUserEntity extends AbstractBaseEntity{

	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 已提现总额
	 */
	private BigDecimal cash;
	/**
	 *  可用金额
	 */
	private BigDecimal availableMoney;
	/**
	 *  冻结金额
	 */
	private BigDecimal frostMoney;
	/**
	 *  货币类型码，默认人民币CNY
	 */
	private String currencyIso;
	/**
	 *  账户状态，0可用1锁定
	 */
	private int isLock;
	 
}
