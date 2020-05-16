package com.bs.payment.common.constans;

/**
 * 系统常量
 * @author gunzhenling
 */
public class Consts {
	
	public static final String SUCCESS = "SUCCESS";
	 
	public static final String FAIL = "FAIL";
	
	public static final String YES = "yes";
	
	public static final String NO = "no";
	
	public static final	 String SELL_BILL_VERSION		= "001";
	
	/**
	 * 文件类型
	 * @author gunzhenling
	 */
	public static class FilelType {
		public static final String IMAGE = "image";
		public static final String DOCUMENT = "document"; 
	}
	
	/**
	 * 账户状态，0可用1锁定
	 * @author gunzhenling
	 */
	public static class BankLockStatus {
		public static final int AVAIL = 0;
		public static final int LOCK = 1; 
	}
	
	/**
	 * 货币类型码，默认人民币CNY
	 * @author gunzhenling
	 */
	public static class MonenyType {
		public static final String CNY = "CNY";
	}
	
	/**
	 * 地址 是否默认，0不是，1是
	 * @author gunzhenling
	 */
	public static class AddressDefaultType {
		public static final int YES = 1;
		public static final int NO = 0; 
	}
	
	/**
	 * 单号类型
	 * @author gunzhenling
	 */
	public static class OrderType {
		public static final String PAY = "pay";
		public static final String REFUND = "refund"; 
		public static final String BANK_AVAIL = "bank_avail"; //账户余额流水号
	}
	
	/**
	 * 发货状态
	 * 确认收货状态：0未发货，1已发货，2已确认收货，3已退货，4退货中
	 * @author gunzhenling
	 */
	public static class ShipStatus {
		public static final int NO = 0;
		public static final int SENT = 1;
		public static final int CONFIRM = 2;
		public static final int REFUNDED = 3;
		public static final int REFUNDING =4;
	}
	
	   
	
	/**
	 * 支付状态
	 * 支付状态：0待支付，1:支付失败或取消订单，2:支付完成，3退款中，4退款完成，5冻结中
	 * @author gunzhenling
	 */
	public static class PayStatus {
		public static final int NO = 0;
		public static final int SENT = 1;
		public static final int CONFIRM = 2;
		public static final int REFUNDED = 3;
		public static final int REFUNDING =4;
	}
	
	
	
	 
}
