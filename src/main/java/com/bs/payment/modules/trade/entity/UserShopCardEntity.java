package com.bs.payment.modules.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户余额表
 * 
 * @author gunzhenling
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("bs_user_shop_card")
public class UserShopCardEntity extends AbstractBaseEntity{

	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 礼品编码
	 */
	private Integer giftCode;
	/**
	 * 礼品名称
	 */
	private String giftName;
	 
	/**
	 * 单个原始价格
	 */
	private BigDecimal giftPrice;
	/**
	 * 单个实际价格
	 */
	private BigDecimal realGiftPrice;
	
	/**
	 *  订单总额=单个原始价格*购买商品数量+运费
	 */
	private BigDecimal sellIncome;
	/**
	 *  实付金额=单个实际价格*购买商品数量+运费
	 */
	private BigDecimal buyerPayAmount;
	 
	/**
	 *   订单支付失效时间
	 */
	private Date expirationTime;
	
	/**
	 * 规格,json
	 */
	private String specification;
	/**
	 * 定制,json
	 */
	private String customMade;
	 
}
