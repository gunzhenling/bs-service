package com.bs.payment.modules.trade.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import lombok.ToString;
import lombok.experimental.Accessors;
/**
 * 礼品类型表
 * 
 * @author zhenling
 */
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("bs_gift_type")
public class BsGiftTypeEntity extends AbstractBaseEntity{

	/**
	 * 类型编码
	 */
	private Integer typeCode;
	/**
	 * 类型名称
	 */
	private String typeName;
	 
	/**
	 * 排序，数值，越大越在前面
	 */
	private Integer sort;
	 

}
