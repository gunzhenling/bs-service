package com.bs.payment.modules.trade.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  购物车提交请求信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="购物车提交请求信息")
public class ShopCommitReqVO {
	
	@NotNull(message="用户主键id不能为空")
	@ApiModelProperty(name="用户主键id")
	@JSONField(name="user_id")
	@JsonProperty("user_id")
	private Long userId;
	
	@NotNull(message="礼品编码不能为空")
	@ApiModelProperty(name="礼品编码")
	@JSONField(name="gift_code")
	@JsonProperty("gift_code")
	private Integer giftCode;
	
	@NotNull(message="购买商品数量不能为空")
	@ApiModelProperty(name="购买商品数量")
	@JSONField(name="gift_amount")
	@JsonProperty("gift_amount")
	private Integer giftAmount;
	
	@NotNull(message="订单总额不能为空")
	@ApiModelProperty(name="订单总额=单个原始价格*购买商品数量")
	@JSONField(name="sell_income")
	@JsonProperty("sell_income")
	private BigDecimal sellIncome;
	
	
	@NotNull(message="实付金额不能为空")
	@ApiModelProperty(name="实付金额=单个实际价格*购买商品数量 （未包含运费）")
	@JSONField(name="buyer_pay_amount")
	@JsonProperty("buyer_pay_amount")
	private BigDecimal buyerPayAmount;
	
	@NotNull(message="规格信息不能为空")
	@ApiModelProperty(name="规格信息")
	private SpecificationVO specification;
	
	@NotNull(message="定制信息不能为空")
	@ApiModelProperty(name="定制信息")
	@JSONField(name="custom_made")
	@JsonProperty("custom_made")
	private CustomMadeVO customMade;
	 
	/**
	 * 礼品图片 前端接收文件类型
	 */
	@ApiModelProperty(value="礼品图片Url")
	@JSONField(name="picture_url")
	@JsonProperty("picture_url")
	private String pictureUrl;

}
