package com.bs.payment.modules.trade.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
/**
 * 礼品类型表
 * 
 * @author zhenling
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("bs_gift_type")
public class BsGiftTypeEntity extends AbstractBaseEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
