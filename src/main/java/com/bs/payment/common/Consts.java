package com.bs.payment.common;

/**
 * 系统常量
 * @author gunzhenling
 */
public class Consts {
	
	public static final String SUCCESS = "SUCCESS";
	 
	public static final String FAIL = "FAIL";
	
	/**
	 * 文件类型
	 * @author gunzhenling
	 */
	public static class FilelType {
		public static final String IMAGE = "image";
		public static final String DOCUMENT = "document"; 
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
