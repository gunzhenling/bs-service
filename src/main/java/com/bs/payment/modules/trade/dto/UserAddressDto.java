package com.bs.payment.modules.trade.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 收货地址
 * @author zhenling
 *
 */ 
@Data
@ApiModel(value="收货地址信息")
public class UserAddressDto {
	
	/**
	 * 原地址id
	 */
	@ApiModelProperty(name="原地址id-更新地址必传")
	@JSONField(name="user_address_id")
	@JsonProperty("user_address_id")
	private Long userAddressId;

	/**
	 * 用户主键id
	 */
	@NotNull(message="用户主键id不能为空")
	@ApiModelProperty(name="用户主键id")
	private Long userId;
	
	/**
	 * 
	 * 是否默认，0不是，1是
	 */
	@NotNull(message="是否默认地址不能为空")
	@ApiModelProperty(name="是否默认，0不是，1是")
	@JSONField(name="is_default")
	@JsonProperty("is_default")
	private Integer isDefault;
	
	/**
	 * 收货人
	 */
	@NotEmpty(message="收货人不能为空")
	@ApiModelProperty(name="收货人")
	private String name;
	/**
	 * 手机号
	 */
	@NotEmpty(message="手机号不能为空")
	@ApiModelProperty(name="手机号")
	private String phone;
	/**
	 * 国家
	 */
	@NotEmpty(message="国家不能为空")
	@ApiModelProperty(name="国家")
	private String country;
	/**
	 * 省
	 */
	@NotEmpty(message="省不能为空")
	@ApiModelProperty(name="省")
	private String province;
	/**
	 * 市
	 */
	@NotEmpty(message="市不能为空")
	@ApiModelProperty(name="市")
	private String city;
	/**
	 * 区
	 */
	@NotEmpty(message="区不能为空")
	@ApiModelProperty(name="区")
	private String district;
	/**
	 * 详细地址-门牌号
	 */
	@NotEmpty(message="详细地址-门牌号不能为空")
	@ApiModelProperty(name="详细地址-门牌号")
	private String address; 
}
