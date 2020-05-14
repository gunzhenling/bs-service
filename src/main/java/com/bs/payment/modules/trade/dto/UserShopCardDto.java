package com.bs.payment.modules.trade.dto;

import java.math.BigDecimal;
import java.util.Date;

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
	private Long userId;
	 
	@ApiModelProperty(name="礼品编码")
	private Integer giftCode;
	 
	@ApiModelProperty(name="礼品名称")
	private String giftName;
	 
	@ApiModelProperty(name="单个原始价格")
	private BigDecimal giftPrice;
	 
	@ApiModelProperty(name="单个实际价格")
	private BigDecimal realGiftPrice;
	 
	@ApiModelProperty(name="订单总额=单个原始价格*购买商品数量+运费")
	private BigDecimal sellIncome;
	 
	@ApiModelProperty(name="实付金额=单个实际价格*购买商品数量+运费")
	private BigDecimal buyerPayAmount;
	 
	 
	@ApiModelProperty(name="购物车失效时间")
	private Date expirationTime;
	
	 
	@ApiModelProperty(name="规格,json")
	private String specification;
	 
	@ApiModelProperty(name="定制,json")
	private String customMade;
	
	@ApiModelProperty(name="创建时间")
	private Date	createTime;
	 
}
