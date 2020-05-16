package com.bs.payment.modules.trade.entity;



import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
/**
 * 用户收藏礼品表
 * 
 * @author zhenling
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("bs_user_gift_like")
public class UserGiftLikeEntity  extends AbstractBaseEntity{
	
	/**
	 * 礼品编码
	 */
	private Integer giftCode;
	/**
	 * 用户主键id
	 */
	private Long userId;

}
