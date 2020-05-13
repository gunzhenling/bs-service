package com.bs.payment.modules.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bs.payment.common.entity.AbstractBaseEntity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 礼品表
 * 
 * @author gunzhenling
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("bs_gift_type")
public class BsGiftInfoEntity extends AbstractBaseEntity{

	/**
	 * 礼品编码
	 */
	private Integer giftCode;
	/**
	 * 礼品名称
	 */
	private String giftName;
	/**
	 * 礼品类型编码
	 */
	private Integer typeCode;
	/**
	 * 单个原始价格
	 */
	private BigDecimal giftPrice;
	/**
	 * 单个实际价格
	 */
	private BigDecimal realGiftPrice;
	/**
	 * 礼品内容,html
	 * longtext
	 */
	private String content;
	
	/**
	 * 已售数量
	 */
	private Integer saleNum;
	/**
	 * 定制至少售卖数量
	 */
	private Integer limitNum;
	/**
	 * 规格,json
	 */
	private String specification;
	/**
	 * 定制,json
	 */
	private String customMade;
	/**
	 * 礼品图片 MEDIUMBLOB
	 * java类型为Object 方便转型
	 */
	private Object picture;
	
}
