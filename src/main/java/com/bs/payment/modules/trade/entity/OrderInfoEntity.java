package com.bs.payment.modules.trade.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@TableName("bs_order_info")
public class OrderInfoEntity extends AbstractBaseEntity  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户主键
	 */
	private Integer userId;
	
	/**
	 *  订单号
	 */
	private String orderNo;
	/**
	 *  礼品编码
	 */
	private Integer giftCode;
	/**
	 *  
	 */
	private String giftName;
	/**
	 *  购买商品数量
	 */
	private Integer giftAmount;
	/**
	 *  单个原始价格
	 */
	private BigDecimal giftPrice;
	/**
	 *  单个实际价格
	 */
	private BigDecimal realGiftPrice;
	/**
	 *  运费
	 */
	private BigDecimal freightPrice;
	/**
	 *  订单总额=单个原始价格*购买商品数量+运费
	 */
	private BigDecimal sellIncome;
	/**
	 *  实付金额=单个实际价格*购买商品数量+运费
	 */
	private BigDecimal buyerPayAmount;
	/**
	 *  支付状态：0待支付，1:支付失败或取消订单，2:支付完成，3退款中，4退款完成，5冻结中
	 */
	private Integer payStatus;
	
	/**
	 *   确认收货状态：0未发货，1已发货，2已确认收货，3已退货，4退货中
	 */
	private Integer shipStatus;
	/**
	 *   支付时间
	 */
	private Date payTime;
	/**
	 *   确认发货时间
	 */
	private Date shipTime;
	/**
	 *   结算状态，0未结算，1可结算，2已结算，3人工锁定,6待审核
	 */
	private Integer settleStatus;
	/**
	 *   结算时间
	 */
	private Date settleTime;
	/**
	 *   退款状态：0默认值，1全部退款，2部分退款
	 */
	private Integer refundType;
	/**
	 *   订单退款
	 */
	private BigDecimal refundIncome;
	/**
	 *   支付商户号
	 */
	private String merchantNo;
	/**
	 *   支付渠道  alipay wx
	 */
	private String payChannel;
	/**
	 *   订单支付失效时间
	 */
	private Date expirationTime;
	/**
	 *   收货地址,json,因为地址信息会变化，某个订单的地址是固定的，所以直接存json
	 */
	private String userAddressJson;
	/**
	 *   规格,json
	 */
	private String specification;
	/**
	 *  定制,json
	 */
	private String customMade;

}
