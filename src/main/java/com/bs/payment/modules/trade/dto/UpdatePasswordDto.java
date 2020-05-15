package com.bs.payment.modules.trade.dto;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户更改密码请求信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="用户更改密码请求信息")
public class UpdatePasswordDto {

	/**
	 * 用户昵称
	 */
	@NotBlank(message="用户昵称不能为空")
	@ApiModelProperty(name="用户昵称")
	private String name;
	  
	/**
	 *  旧密码 
	 */
	@NotBlank(message="旧密码不能为空")
	@ApiModelProperty(name="旧密码")
	@JSONField(name="old_password")
	@JsonProperty("old_password")
	private String oldPassword;
	
	/**
	 *  密码,md5后存储
	 */
	@NotBlank(message="新密码不能为空")
	@ApiModelProperty(name="新密码")
	private String password;
	
	
	/**
	 *  确认新密码, 
	 */
	@NotBlank(message="确认新密码不能为空")
	@ApiModelProperty(name="确认新密码")
	@JSONField(name="confirm_password")
	@JsonProperty("confirm_password")
	private String confirmPassword;
	 
}
