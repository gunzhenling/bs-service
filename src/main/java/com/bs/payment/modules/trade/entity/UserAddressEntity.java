package com.bs.payment.modules.trade.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户地址表
 * 
 * @author zhenling
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("bs_user_address")
public class UserAddressEntity extends AbstractBaseEntity  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户主键id
	 */
	private Integer userId;
	/**
	 * 
	 * 是否默认，0不是，1是
	 */
	private Integer isDefault;

	/**
	 * 收货人
	 */
	private String name;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String district;
	/**
	 * 详细地址-门牌号
	 */
	private String address; 
	

}
