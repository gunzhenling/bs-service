package com.bs.payment.modules.trade.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  订单提交请求信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="订单提交请求信息")
public class OrderCommitReqVO {
	
	@ApiModelProperty(name="用户主键id")
	private Integer userId;
	
	@ApiModelProperty(name="礼品编码")
	private Integer giftCode;
	
	@ApiModelProperty(name="购买商品数量")
	private Integer giftAmount;
	
	@ApiModelProperty(name="订单总额=单个原始价格*购买商品数量")
	private BigDecimal sellIncome;
	
	@ApiModelProperty(name="实付金额=单个实际价格*购买商品数量 （未包含运费）")
	private BigDecimal buyerPayAmount;
	
	@ApiModelProperty(name="规格")
	private String specification;
	
	@ApiModelProperty(name="定制")
	private String customMade;
	
	@ApiModelProperty(name="收货地址id")
	private Integer userAddressId;

}
