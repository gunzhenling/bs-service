package com.bs.payment.modules.trade.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.bs.payment.modules.trade.dto.UserAddressDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  订单提交响应信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="订单提交响应信息")
public class OrderCommitRespVO {
	
	 
	@ApiModelProperty(name="订单号")
	@JSONField(name="order_no")
	@JsonProperty("order_no")
	private String orderNo;
	
	/**
	 * 用户实付金额
	 */
	@ApiModelProperty(name="用户实付金额")
	@JSONField(name="buyer_pay_amount")
	@JsonProperty("buyer_pay_amount")
	private String buyerPayAmount;
	/**
	 * 收货信息
	 */
	@ApiModelProperty(name="收货信息")
	@JSONField(name="user_address_dto")
	@JsonProperty("user_address_dto")
	private UserAddressDto userAddressDto;

}
