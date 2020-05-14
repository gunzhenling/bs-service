package com.bs.payment.modules.trade.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录请求
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="用户登录请求信息")
public class UserLoginDto {

	/**
	 * 用户昵称
	 */
	@NotBlank(message="用户昵称不能为空")
	@ApiModelProperty(name="用户昵称")
	private String name;
	 
	/**
	 *  密码,md5后存储
	 */
	@NotBlank(message="密码不能为空")
	@ApiModelProperty(name="密码")
	private String password;
	 
}
