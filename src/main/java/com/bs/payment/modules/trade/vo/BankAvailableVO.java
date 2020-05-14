package com.bs.payment.modules.trade.vo;

import java.util.List;

import com.bs.payment.modules.trade.dto.BankUserAvailableHistoryDto;
import com.bs.payment.modules.trade.dto.BankUserDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户账户+余额信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="用户账户+余额信息")
public class BankAvailableVO {
	
	@ApiModelProperty(name="用户账户信息")
	private BankUserDto bankUserDto;
	
	@ApiModelProperty(name="用户余额信息")
	private List<BankUserAvailableHistoryDto> bankUserAvailableHistoryDtos;

}
