package com.bs.payment.modules.trade.dto;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付请求dto
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="支付请求dto")
public class PayDto {
	
	
	@ApiModelProperty(name="用户主键id") 
	private Long  userId;
	
	@ApiModelProperty(name="订单后") 
	private String  orderNo;
	
	@ApiModelProperty(name="支付渠道") 
	private String  payChannel;
	
	
	@ApiModelProperty(name="支付金额")
	private BigDecimal payMoney;

}
