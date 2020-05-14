package com.bs.payment.modules.trade.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单信息
 * 
 * @author gunzhenling
 */
@Data
@ApiModel(value="订单信息")
public class BgOrderInfoRespVO {
	

	/**
	 * 用户主键
	 */
	@ApiModelProperty(value="用户主键")
	@JSONField(name="user_id")
	@JsonProperty("user_id")
	private Integer userId;
	
	/**
	 *  订单号
	 */
	@ApiModelProperty(value="订单号")
	@JSONField(name="order_no")
	@JsonProperty("order_no")
	private String orderNo;
	/**
	 *  礼品编码
	 */
	@ApiModelProperty(value="礼品编码")
	@JSONField(name="gift_code")
	@JsonProperty("gift_code")
	private Integer giftCode;
	/**
	 *  礼品名称
	 */
	@ApiModelProperty(value="礼品名称")
	@JSONField(name="gift_name")
	@JsonProperty("gift_name")
	private String giftName;
	/**
	 *  购买商品数量
	 */
	@ApiModelProperty(value="购买商品数量")
	@JSONField(name="gift_amount")
	@JsonProperty("gift_amount")
	private Integer giftAmount;
	/**
	 *  单个原始价格
	 */
	@ApiModelProperty(value="单个原始价格")
	@JSONField(name="gift_price")
	@JsonProperty("gift_price")
	private BigDecimal giftPrice;
	/**
	 *  单个实际价格
	 */
	@ApiModelProperty(value="单个实际价格")
	@JSONField(name="real_gift_price")
	@JsonProperty("real_gift_price")
	private BigDecimal realGiftPrice;
	/**
	 *  运费
	 */
	@ApiModelProperty(value="运费")
	@JSONField(name="freight_price")
	@JsonProperty("freight_price")
	private BigDecimal freightPrice;
	/**
	 *  订单总额=单个原始价格*购买商品数量+运费
	 */
	@ApiModelProperty(value="订单总额=单个原始价格*购买商品数量+运费")
	@JSONField(name="sell_income")
	@JsonProperty("sell_income")
	private BigDecimal sellIncome;
	/**
	 *  实付金额=单个实际价格*购买商品数量+运费
	 */
	@ApiModelProperty(value="实付金额=单个实际价格*购买商品数量+运费")
	@JSONField(name="buyer_pay_amount")
	@JsonProperty("buyer_pay_amount")
	private BigDecimal buyerPayAmount;
	/**
	 *  支付状态：0待支付，1:支付失败或取消订单，2:支付完成，3退款中，4退款完成，5冻结中
	 */
	@ApiModelProperty(value="付状态：0待支付，1:支付失败或取消订单，2:支付完成，3退款中，4退款完成，5冻结中")
	@JSONField(name="pay_status")
	@JsonProperty("pay_status")
	private Integer payStatus;
	
	/**
	 *   确认收货状态：0未发货，1已发货，2已确认收货，3已退货，4退货中
	 */
	@ApiModelProperty(value="确认收货状态：0未发货，1已发货，2已确认收货，3已退货，4退货中")
	@JSONField(name="ship_status")
	@JsonProperty("ship_status")
	private Integer shipStatus;
	/**
	 *   支付时间
	 */
	@ApiModelProperty(value="支付时间")
	@JSONField(name="pay_time")
	@JsonProperty("pay_time")
	private Date payTime;
	/**
	 *   确认发货时间
	 */
	@ApiModelProperty(value="确认发货时间")
	@JSONField(name="ship_time")
	@JsonProperty("ship_time")
	private Date shipTime;
	/**
	 *   结算状态，0未结算，1可结算，2已结算，3人工锁定,6待审核
	 */
	@ApiModelProperty(value="结算状态，0未结算，1可结算，2已结算，3人工锁定,6待审核")
	@JSONField(name="settle_status")
	@JsonProperty("settle_status")
	private Integer settleStatus;
	/**
	 *   结算时间
	 */
	@ApiModelProperty(value="结算时间")
	@JSONField(name="settle_time")
	@JsonProperty("settle_time")
	private Date settleTime;
	/**
	 *   退款状态：0默认值，1全部退款，2部分退款
	 */
	@ApiModelProperty(value="退款状态：0默认值，1全部退款，2部分退款")
	@JSONField(name="refund_type")
	@JsonProperty("refund_type")
	private Integer refundType;
	/**
	 *   订单退款
	 */
	@ApiModelProperty(value="订单退款")
	@JSONField(name="refund_income")
	@JsonProperty("refund_income")
	private BigDecimal refundIncome;
	/**
	 *   支付商户号
	 */
	@ApiModelProperty(value="支付商户号")
	@JSONField(name="merchant_no")
	@JsonProperty("merchant_no")
	private String merchantNo;
	/**
	 *   支付渠道  alipay wx
	 */
	@ApiModelProperty(value="支付渠道  alipay wx")
	@JSONField(name="pay_channel")
	@JsonProperty("pay_channel")
	private String payChannel;
	/**
	 *   订单支付失效时间
	 */
	@ApiModelProperty(value="单支付失效时间")
	@JSONField(name="expiration_time")
	@JsonProperty("expiration_time")
	private Date expirationTime;
	/**
	 *   收货地址,json,因为地址信息会变化，某个订单的地址是固定的，所以直接存json
	 */
	
	@ApiModelProperty(value="收货地址,json因为地址信息会变化，某个订单的地址是固定的，所以直接存json")
	@JSONField(name="user_address_json")
	@JsonProperty("user_address_json")
	private String userAddressJson;
	/**
	 *   规格,json
	 */
	@ApiModelProperty(value="规格,json")
	@JSONField(name="specification")
	@JsonProperty("specification")
	private String specification;
	/**
	 *  定制,json
	 */
	@ApiModelProperty(value="定制,json")
	@JSONField(name="custom_made")
	@JsonProperty("custom_made")
	private String customMade;
	/**
	 *  创建时间
	 */
	@ApiModelProperty(value="创建时间")
	@JSONField(name="create_time")
	@JsonProperty("create_time")
	private Date createTime;
}
