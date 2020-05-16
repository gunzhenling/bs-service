package com.bs.payment.modules.trade.dto;



import java.util.Date;

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
	
	private Long id;

	/**
	 * 礼品编码
	 */

	@JSONField(name="gift_code")
	@JsonProperty("gift_code")
	private String giftCode;
	/**
	 * 用户主键id
	 */
	@JSONField(name="user_id")
	@JsonProperty("user_id")
	private Long userId;
	

	@JSONField(name="create_time")
	@JsonProperty("create_time")
	private Date	createTime;

}
