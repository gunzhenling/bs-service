package com.bs.payment.modules.trade.dto;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户余额
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="用户账户")
public class BankUserDto {

	/**
	 * 用户ID
	 */
	@ApiModelProperty(name="用户ID")
	@JSONField(name="user_id")
	@JsonProperty("user_id")
	private Long userId;
	/**
	 * 已提现总额
	 */
	@ApiModelProperty(name="已提现总额")
	private BigDecimal cash;
	/**
	 *  可用金额
	 */
	@ApiModelProperty(name="可用金额")
	@JSONField(name="available_money")
	@JsonProperty("available_money")
	private BigDecimal availableMoney;
	/**
	 *  冻结金额
	 */
	@ApiModelProperty(name="冻结金额")
	@JSONField(name="frost_money")
	@JsonProperty("frost_money")
	private BigDecimal frostMoney;
	/**
	 *  货币类型码，默认人民币CNY
	 */
	@ApiModelProperty(name="货币类型码，默认人民币CNY")
	@JSONField(name="currency_iso")
	@JsonProperty("currency_iso")
	private String currencyIso;
	/**
	 *  账户状态，0可用1锁定
	 */
	@ApiModelProperty(name="账户状态，0可用1锁定")
	@JSONField(name="is_lock")
	@JsonProperty("is_lock")
	private int isLock;
	 
}
