package com.bs.payment.modules.trade.dto;

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
	@ApiModelProperty(name="原地址id")
	private Integer userAddressId;

	/**
	 * 用户主键id
	 */
	@ApiModelProperty(name="用户主键id")
	private Integer userId;
	
	/**
	 * 
	 * 是否默认，0不是，1是
	 */
	@ApiModelProperty(name="是否默认，0不是，1是")
	private Integer isDefault;
	
	/**
	 * 收货人
	 */
	@ApiModelProperty(name="收货人")
	private String name;
	/**
	 * 手机号
	 */
	@ApiModelProperty(name="手机号")
	private String phone;
	/**
	 * 国家
	 */
	@ApiModelProperty(name="国家")
	private String country;
	/**
	 * 省
	 */
	@ApiModelProperty(name="省")
	private String province;
	/**
	 * 市
	 */
	@ApiModelProperty(name="市")
	private String city;
	/**
	 * 区
	 */
	@ApiModelProperty(name="区")
	private String district;
	/**
	 * 详细地址-门牌号
	 */
	@ApiModelProperty(name="详细地址-门牌号")
	private String address; 
}
