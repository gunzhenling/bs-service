package com.bs.payment.modules.trade.vo;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付下单请求
 * 
 * @author fanhang
 */
@Data
@Accessors(chain = true)
@ApiModel(value="支付下单请求")
public class PayRequestVO  {
	
	@NotBlank(message = "APPID不能为空")
	private String appId;

	@ApiModelProperty(hidden=true)
	private String payType;
	
	@ApiModelProperty(value="交易类型", hidden=true)
	private String tradeType;

	@NotBlank(message = "支付渠道不能为空")
	@ApiModelProperty(value="支付渠道", required=true, allowableValues="alipay_qr, alipay_app, alipay_wap, wx_qr, wx_app, wx_wap, wx_pub, wx_lite")
	private String channel;
	
	@NotBlank(message = "内部订单号不能为空")
	@ApiModelProperty(value="内部订单号", required=true)
	private String orderNo;
	
	@NotNull(message = "支付金额不能为空")
	@DecimalMin(value = "1", message = "支付金额不能少于1分")
	@ApiModelProperty(value="支付金额", required=true)
	private Integer amount;

	@NotBlank(message = "订单/商品标题不能为空")
	@ApiModelProperty(value="订单/商品标题", required=true)
	private String subject;
	
	@ApiModelProperty(value="超时时间,分钟(1<= timeout <= 120)", notes = "范围: 1<= timeout <= 120, 不设置则为支付平台默认策略")
	private Integer timeout;
	
	@ApiModelProperty(value="客户端IP")
	private String clientIp;

	@ApiModelProperty(value="扩展数据", example="wx_pub与wx_lite与alipay_lite必须设置extra.openid", notes="wx_pub与")
	private Map<String, String> extra = new HashMap<>();
	
	public void putExtra(String key, String value) {
		this.extra.put(key, value);
	}
	 
}
