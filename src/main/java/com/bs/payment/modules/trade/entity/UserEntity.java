package com.bs.payment.modules.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户表
 * 
 * @author gunzhenling
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("bs_user")
public class UserEntity extends AbstractBaseEntity{

	/**
	 * 用户昵称
	 */
	private String name;
	/**
	 * 用户头像
	 */
	private String icon;
	/**
	 *  手机号
	 */
	private String phone;
	/**
	 *  密码,md5后存储
	 */
	private String password;
	 
}
