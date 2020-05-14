package com.bs.payment.modules.trade.vo;

import com.bs.payment.modules.trade.dto.BankUserDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户个人信息和账户信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="用户个人信息和账户信息")
public class UserBankRespVO {
	
	@ApiModelProperty(name="用户个人信息")
	private UserRespVO userRespVO;
	
	@ApiModelProperty(name="用户账户信息")
	private BankUserDto bankUserDto;
	
 
}
