package com.bs.payment.modules.trade.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单提交信息
 * @author zhenling
 *
 */
@Data
@ApiModel(value="订单提交信息")
public class UpdateShipStatusVO {
	
	/**
	 *  订单号
	 */
	@ApiModelProperty(value="订单号")
	@JSONField(name="order_no")
	@JsonProperty("order_no")
	private String orderNo;
	
	/**
	 *  发货状态
	 */
	@ApiModelProperty(value="发货状态")
	@JSONField(name="ship_status")
	@JsonProperty("ship_status")
	private Integer shipStatus;

}
