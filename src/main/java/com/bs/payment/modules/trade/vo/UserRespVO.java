package com.bs.payment.modules.trade.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="用户信息注册登录返回信息")
public class UserRespVO {
	
	
	@ApiModelProperty(name="用户主键")
	@JSONField(name="user_id")
	@JsonProperty("user_id")
	private Long userId;
	
	/**
	 * 用户昵称
	 */
	@ApiModelProperty(name="用户昵称")
	private String name;
	/**
	 * 用户头像
	 */
	@ApiModelProperty(name="用户头像")
	private String icon;
	/**
	 *  手机号
	 */
	@ApiModelProperty(name="phone")
	private String phone;
	 
	 
}
