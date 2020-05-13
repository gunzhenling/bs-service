/**
 * 
 */
package com.bs.payment.common.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 描述：mybatis 基础类
 * 
 * @author gunzhenling
 * @version:  
 *
 */
@Data
@Accessors(chain = true)
public abstract class AbstractBaseEntity {
	@TableId(type = IdType.AUTO)
	private Long	 id;
	@TableLogic // 软删标记
	@TableField(fill = FieldFill.INSERT) // 插入自动填充
	private Integer	status;
	@TableField(fill = FieldFill.INSERT) // 插入自动填充
	private Date	createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE) // 插入更新自动填充
	private Date	updateTime;
}
