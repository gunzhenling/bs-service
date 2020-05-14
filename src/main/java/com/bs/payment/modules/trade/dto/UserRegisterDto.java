package com.bs.payment.modules.trade.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="用户注册请求信息")
public class UserRegisterDto {

	/**
	 * 用户昵称
	 */
	@NotBlank(message="用户昵称不能为空")
	@ApiModelProperty(name="用户昵称")
	private String name;
	/**
	 * 用户头像
	 */
	@NotBlank(message="用户头像不能为空")
	@ApiModelProperty(name="用户头像")
	private String icon;
	/**
	 *  手机号
	 */
	@ApiModelProperty(name="phone")
	private String phone;
	/**
	 *  密码,md5后存储
	 */
	@NotBlank(message="密码不能为空")
	@ApiModelProperty(name="密码")
	private String password;
	 
}
