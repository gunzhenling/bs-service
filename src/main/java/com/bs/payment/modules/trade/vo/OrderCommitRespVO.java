package com.bs.payment.modules.trade.vo;

import com.bs.payment.modules.trade.dto.UserAddressDto;

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
	private String orderNo;
	
	/**
	 * 用户实付金额
	 */
	@ApiModelProperty(name="用户实付金额")
	private String buyerPayAmount;
	/**
	 * 收货信息
	 */
	@ApiModelProperty(name="收货信息")
	private UserAddressDto userAddressDto;

}
