package com.bs.payment.modules.trade.dto;



import javax.validation.constraints.NotEmpty;
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
	@NotEmpty(message="礼品编码不能为空")
	@JSONField(name="gift_code")
	@JsonProperty("gift_code")
	private String giftCode;
	/**
	 * 用户主键id
	 */
	@NotNull(message="用户主键id不能为空")
	@JSONField(name="user_id")
	@JsonProperty("user_id")
	private Long userId;

}
