package com.bs.payment.modules.trade.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户余额表
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="用户购物车信息")
public class UserShopCardDto  {

	@ApiModelProperty(name="用户ID")
	@JSONField(name="user_id")
	@JsonProperty("user_id")
	private Long userId;
	 
	@ApiModelProperty(name="礼品编码")
	@JSONField(name="gift_code")
	@JsonProperty("gift_code")
	private Integer giftCode;
	 
	@ApiModelProperty(name="礼品名称")
	@JSONField(name="gift_name")
	@JsonProperty("gift_name")
	private String giftName;
	 
	@ApiModelProperty(name="单个原始价格")
	@JSONField(name="gift_price")
	@JsonProperty("gift_price")
	private BigDecimal giftPrice;
	 
	@ApiModelProperty(name="单个实际价格")
	@JSONField(name="real_gift_price")
	@JsonProperty("real_gift_price")
	private BigDecimal realGiftPrice;
	 
	@ApiModelProperty(name="订单总额=单个原始价格*购买商品数量+运费")
	@JSONField(name="sell_income")
	@JsonProperty("sell_income")
	private BigDecimal sellIncome;
	 
	@ApiModelProperty(name="实付金额=单个实际价格*购买商品数量+运费")
	@JSONField(name="buyer_pay_amount")
	@JsonProperty("buyer_pay_amount")
	private BigDecimal buyerPayAmount;
	 
	 
	@ApiModelProperty(name="购物车失效时间")
	@JSONField(name="expiration_time")
	@JsonProperty("expiration_time")
	private Date expirationTime;
	
	 
	@ApiModelProperty(name="规格,json")
	private String specification;
	 
	@ApiModelProperty(name="定制,json")
	@JSONField(name="custom_made")
	@JsonProperty("custom_made")
	private String customMade;
	
	@ApiModelProperty(name="创建时间")
	@JSONField(name="create_time")
	@JsonProperty("create_time")
	private Date	createTime;
	 
}
