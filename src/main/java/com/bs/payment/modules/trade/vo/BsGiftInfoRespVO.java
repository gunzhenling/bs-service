package com.bs.payment.modules.trade.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 礼品返回信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="礼品返回信息")
public class BsGiftInfoRespVO  {
	 
	/**
	 * 礼品编码
	 */
	@ApiModelProperty(value="礼品编码")
	@JSONField(name="gift_code")
	@JsonProperty("gift_code")
	private Integer giftCode;
	/**
	 * 礼品名称
	 */
	@ApiModelProperty(value="礼品名称")
	@JSONField(name="gift_name")
	@JsonProperty("gift_name")
	private String giftName;
	/**
	 * 礼品类型编码
	 */
	@ApiModelProperty(value="礼品类型编码")
	@JSONField(name="type_code")
	@JsonProperty("type_code")
	private Integer typeCode;
	/**
	 * 单个原始价格
	 */
	@ApiModelProperty(value="单个原始价格")
	@JSONField(name="gift_price")
	@JsonProperty("gift_price")
	private BigDecimal giftPrice;
	/**
	 * 单个实际价格
	 */
	@ApiModelProperty(value="单个实际价格")
	@JSONField(name="real_gift_price")
	@JsonProperty("real_gift_price")
	private BigDecimal realGiftPrice; 
	
	/**
	 * 已售数量
	 */
	@ApiModelProperty(value="已售数量")
	@JSONField(name="sale_num")
	@JsonProperty("sale_num")
	private Integer saleNum;
	/**
	 * 定制至少售卖数量
	 */
	@ApiModelProperty(value="定制至少售卖数量")
	@JSONField(name="limit_num")
	@JsonProperty("limit_num")
	private Integer limitNum;
	
	
	
//	下面内容详情再请求加载
	/**
	 * 礼品内容,html
	 * longtext
	 *//*
	@ApiModelProperty(value="礼品内容,html")
	private String content;
	*//**
	 * 规格,json
	 *//*
	@ApiModelProperty(value="规格,json")
	private String specification;
	*//**
	 * 定制,json
	 *//*
	@ApiModelProperty(value="定制,json")
	private String customMade;
	
	*//**
	 * 礼品图片 MEDIUMBLOB
	 *//*
	@ApiModelProperty(value="礼品图片")
	private byte[] picture;*/
	 
}
