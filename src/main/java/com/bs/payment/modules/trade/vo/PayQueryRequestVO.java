package com.bs.payment.modules.trade.vo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 支付订单查询请求
 * 
 * @author fanhang
 */
@Data
@ApiModel(value="支付订单查询请求")
public class PayQueryRequestVO  {
	
	@NotBlank(message = "APPID不能为空")
	private String appId;
	
//	20190505 gunzl 支持小程序支付 beg
	@ApiModelProperty(value="交易类型", hidden=true)
	private String tradeType;
//	20190505 gunzl 支持小程序支付 end
	
	@NotBlank(message = "支付类型不能为空")
	private String payType;

	@NotBlank(message = "内部订单号不能为空")
	@ApiModelProperty(value="内部订单号", required=true)
	private String orderNo;
	
	 
}
