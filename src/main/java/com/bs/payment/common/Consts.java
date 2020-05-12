package com.bs.payment.common;

/**
 * 系统常量
 * @author fanhang
 */
public class Consts {
	
	/** 支付宝支付类型 - alipay */
	public static final String PAY_TYPE_ALIPAY = "alipay";
	/** 微信支付类型 - wx */
	public static final String PAY_TYPE_WXPAY = "wx";
	
	/**
	 * 支付宝支付渠道
	 * @author fanhang
	 */
	public static class AlipayChannelType {
		public static final String QR = "qr";
		public static final String APP = "app";
		public static final String WAP = "wap";
		public static final String WEB = "web";
		public static final String LITE = "lite";
	}
	
	/**
	 * 微信支付渠道
	 * @author fanhang
	 */
	public static class WxChannelType {
		public static final String QR = "qr";
		public static final String APP = "app";
		public static final String WAP = "wap";
		public static final String PUB = "pub";
		public static final String LITE = "lite";
	}
	
	/**
	 * 消息服务
	 */
	public static class Message {
		/** 支付回调消息 topic */
		public static final String PAY_NOTIFY_TOPIC = "zc-payment-v3_pay-notify";
		/** 退款回调消息 topic */
		public static final String REFUND_NOTIFY_TOPIC = "zc-payment-v3_refund-notify";
		 
	}
	
	/**
	 * 对账单汇总 消息服务
	 */
	public static class PlatFromPayfeeMessage {
		 
		/** 对账单汇总推送  key */
		public static final String PLATFORM_PAYFEE_KEY = "available";
		/** 对账单汇总推送 topic   */
		public static final String PLATFORM_PAYFEE_TOPIC = "platform_account";
	}
	
	/**
	 *  对账单汇总推送 类型
	 */
	public static class PlatFromPayfee {
		/** 支付手续费支出   */
		public static final int ACCOUNT_REDUCE = 2901;
		/** 支付手续费退回  */
		public static final int ACCOUNT_ADD = 2902;
	}
	
	 
}
