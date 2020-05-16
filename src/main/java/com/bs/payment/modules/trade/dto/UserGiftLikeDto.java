package com.bs.payment.modules.trade.dto;



import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Data;
/**
 * 用户收藏礼品
 * 
 * @author zhenling
 */
@Data
@ApiModel(value="用户收藏礼品信息")
public class UserGiftLikeDto  {
	
	/**
	 * 礼品编码
	 */
	@NotNull(message="礼品编码不能为空")
	@JSONField(name="gift_code")
	@JsonProperty("gift_code")
	private Integer giftCode;
	/**
	 * 用户主键id
	 */
	@NotNull(message="用户主键id不能为空")
	@JSONField(name="user_id")
	@JsonProperty("user_id")
	private Long userId;
	
	/**
	 * 礼品名称
	 */
	@NotNull(message="礼品名称不能为空")
	@JSONField(name="gift_name")
	@JsonProperty("gift_name")
	private String  giftName;
	
	/**
	 * 礼品图片
	 */
	@NotNull(message="礼品图片不能为空")
	@JSONField(name="picture")
	@JsonProperty("picture")
	private String  picture;
	
	/**
	 * 已售数量
	 */
	@NotNull(message="已售数量不能为空")
	@JSONField(name="sale_num")
	@JsonProperty("sale_num")
	private Integer saleNum;
	
	/**
	 * 单个实际价格
	 */
	@NotNull(message="单个实际价格不能为空")
	@JSONField(name="real_gift_price")
	@JsonProperty("real_gift_price")
	private Integer realGiftPrice;
	

}
