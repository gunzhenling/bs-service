package com.bs.payment.modules.trade.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户充值请求信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="用户充值请求信息")
public class RechargeAvailableReqVO {
	
	@NotNull(message="用户id不能为空")
	@ApiModelProperty(name="用户主键")
	@JSONField(name="user_id")
	@JsonProperty("user_id")
	private Long userId;
	
	@NotNull(message="充值金额不能为空")
	@ApiModelProperty(name="用户充值金额")
	private BigDecimal moneny;

}
