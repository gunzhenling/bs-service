package com.bs.payment.modules.trade.entity;



import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
/**
 *  留言信息
 * 
 * @author zhenling
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("bs_common_problem")
public class CommonProblemEntity  extends AbstractBaseEntity{
	
	/**
	 * 标题
	 */
	private Long userId;
	/**
	 * 内容
	 */
	private String content;
	

}
