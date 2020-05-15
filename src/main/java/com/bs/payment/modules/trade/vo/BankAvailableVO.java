package com.bs.payment.modules.trade.vo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.bs.payment.modules.trade.dto.BankUserAvailableHistoryDto;
import com.bs.payment.modules.trade.dto.BankUserDto;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JSONField(name="bank_user_dto")
	@JsonProperty("bank_user_dto")
	private BankUserDto bankUserDto;
	
	@ApiModelProperty(name="用户余额信息")
	@JSONField(name="bank_user_available_history_dtos")
	@JsonProperty("bank_user_available_history_dtos")
	private List<BankUserAvailableHistoryDto> bankUserAvailableHistoryDtos;

}
