package com.bs.payment.modules.trade.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端获取订单列表展示
 * 订单少量信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="前端获取订单列表展示")
public class OrderInfoRespVO {
	

	/**
	 * 用户主键 
	 */
	@ApiModelProperty(value="用户主键")
	@JSONField(name="user_id")
	@JsonProperty("user_id")
	private Long userId;
	
	/**
	 *  订单号
	 */
	@ApiModelProperty(value="订单号")
	@JSONField(name="order_no")
	@JsonProperty("order_no")
	private String orderNo;
	 
	 
	/**
	 *  支付状态：0待支付，1:支付失败或取消订单，2:支付完成，3退款中，4退款完成，5冻结中
	 */
	@ApiModelProperty(value="付状态：0待支付，1:支付失败或取消订单，2:支付完成，3退款中，4退款完成，5冻结中")
	@JSONField(name="pay_status")
	@JsonProperty("pay_status")
	private Integer payStatus;
	
	/**
	 *  支付状态：0待支付，1:支付失败或取消订单，2:支付完成，3退款中，4退款完成，5冻结中
	 */
	@ApiModelProperty(value="付状态：0待支付，1:支付失败或取消订单，2:支付完成，3退款中，4退款完成，5冻结中")
	@JSONField(name="ship_status")
	@JsonProperty("ship_status")
	private Integer shipStatus;
	
	/**
	 *  创建时间
	 */
	@ApiModelProperty(value="创建时间")
	@JSONField(name="create_time")
	@JsonProperty("create_time")
	private Date createTime;
	 

}
