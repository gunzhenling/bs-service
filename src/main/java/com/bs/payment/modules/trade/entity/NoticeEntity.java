package com.bs.payment.modules.trade.entity;



import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
/**
 * 公告表
 * 
 * @author zhenling
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("bs_notice")
public class NoticeEntity  extends AbstractBaseEntity{
	
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;

}
