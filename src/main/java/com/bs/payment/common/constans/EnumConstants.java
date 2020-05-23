package com.bs.payment.common.constans;

/**
 *  枚举常量类
 * @author zhenling
 *
 */
public class EnumConstants {
	
	/**
	 * 结算状态
	 * @author zhenling
	 *
	 */
	public enum SettleStatusEnum{
//		结算状态，0未结算，1可结算，2已结算，3人工锁定,6待审核 |真灵|2019-09-12

		WAIT_AUDIT(0,"未结算"),
		CAN_AUDIT(1,"可结算"),
		IS_AUDIT(2,"已结算"),
		MAN_LOCK(3,"人工锁定"),
		WAIT_VERIFY(6,"待审核");
		
		Integer code;
		String message;
		
		private SettleStatusEnum(Integer code, String message) {
			this.code = code;
			 
			this.message = message;
		}
		
		public Integer getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static Integer getCode(Integer code) {
			
			SettleStatusEnum[] statusEnums=values();
			
			for(SettleStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(Integer code) {
			
			SettleStatusEnum[]  statusEnums=values();
			
			for(SettleStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		 
		
	}
	

	/**
	/**
	 * @author zhenling
	 *
	 */
	public enum MadeTypeEnum{
//	  "made_type": 0,//礼品定制类型，0成品，1定制
		
		DONE(0,"成品"),
		MAN_MADE(1,"定制") ;
		
		Integer code;
		String message;
		
		private MadeTypeEnum(Integer code, String message) {
			this.code = code;
			
			this.message = message;
		}
		
		public Integer getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static Integer getCode(Integer code) {
			
			MadeTypeEnum[] statusEnums=values();
			
			for(MadeTypeEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(Integer code) {
			
			MadeTypeEnum[]  statusEnums=values();
			
			for(MadeTypeEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		
		
	}
	
	/**
	 * 发货状态
	 * @author zhenling
	 *
	 */
	public enum ShipStatusEnum{
//		确认收货状态：0未发货，1已发货，2已确认收货，3已退货，4退货中
		
		WAIT_SHIP(0,"未发货"),
		IS_SHIP(1,"已发货"),
		HAD_SHIP(2,"已确认收货"),
		REFUNDED_SHIP(3,"已退货"),
		REFUNDING_SHIP(4,"退货中");
		
		Integer code;
		String message;
		
		private ShipStatusEnum(Integer code, String message) {
			this.code = code;
			
			this.message = message;
		}
		
		public Integer getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static Integer getCode(Integer code) {
			
			ShipStatusEnum[] statusEnums=values();
			
			for(ShipStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(Integer code) {
			
			ShipStatusEnum[]  statusEnums=values();
			
			for(ShipStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		
		
	}
	
	 

	/**
	 *  退款表退款状态
	 * @author zhenling
	 *
	 */
	public enum RefundStatusEnum{
		
		REFUND_REQUEST(0,"申请退款"),
		REFUND_FAIL(1,"退款失败"),
		REFUND_SUCCESS(2,"退款成功");
		
		Integer code;
		String message;
		
		private RefundStatusEnum(Integer code, String message) {
			this.code = code;
			 
			this.message = message;
		}
		
		public Integer getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static Integer getCode(Integer code) {
			
			RefundStatusEnum[] statusEnums=values();
			
			for(RefundStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(Integer code) {
			
			RefundStatusEnum[] statusEnums=values();
			
			for(RefundStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		
	}

	/**
	 * 支付渠道
	 * @author zhenling
	 *
	 */
	public enum PayChannelEnum{
//		退款类型  alipay wx YuE|滚真灵|2019-09-16
		ALIPAY("alipay","支付宝"),
		WX("wx","微信"),
		YU_E("YuE","余额");
		
		String code;
		String message;
		
		private PayChannelEnum(String code, String message) {
			this.code = code;
			 
			this.message = message;
		}
		
		public String getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static String getCode(String code) {
			
			PayChannelEnum[] statusEnums=values();
			
			for(PayChannelEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode().equals(code)) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(String code) {
			
			PayChannelEnum[] statusEnums=values();
			
			for(PayChannelEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode().equals(code)) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		
	}
	
	/**
	 *  订单支付状态
	 * @author zhenling
	 *
	 */
	public enum PayStatusEnum{
//		支付状态：0待支付，1:支付失败或取消订单，2:支付完成，3退款中，4退款完成，5冻结中
		ORDER_PAY_PENDING(0,"待支付"),
		ORDER_PAY_FAILURE(1,"支付失败或支付超时"),
		ORDER_PAY_SUCCESS(2,"支付成功"),
		ORDER_REFUND_ING(3,"退款中"),
		ORDER_REFUND_SUCCESS(4,"退款完成"),
		ORDER_FROZEN_ING(5,"冻结中");
		
		Integer code;
		String message;
		
		private PayStatusEnum(Integer code, String message) {
			this.code = code;
			
			this.message = message;
		}
		
		public Integer getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static Integer getCode(Integer code) {
			
			PayStatusEnum[] statusEnums=values();
			
			for(PayStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(Integer code) {
			
			PayStatusEnum[] statusEnums=values();
			
			for(PayStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		
	}
	/**
	 * 订单表退款状态
	 * @author zhenling
	 *
	 */
	public enum OrderRefundStatusEnum{
		 
		ORDER_PAY_REFUND_NORMAL(0,"默认值（未退款）"),
		ORDER_PAY_REFUND_ALL(1,"全部退款"),
		ORDER_PAY_REFUND_PART(2,"部分退款");
		
		Integer code;
		String message;
		
		private OrderRefundStatusEnum(Integer code, String message) {
			this.code = code;
			
			this.message = message;
		}
		
		public Integer getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static Integer getCode(Integer code) {
			
			OrderRefundStatusEnum[] statusEnums=values();
			
			for(OrderRefundStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(Integer code) {
			
			OrderRefundStatusEnum[] statusEnums=values();
			
			for(OrderRefundStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		
	}

	/**
	 *  订单结算状态
	 * @author zhenling
	 *
	 */
	public enum  OrderSettleStatusEnum{
		 
		
		ORDER_SETTLE_PENDING(0,"未结算"),
		ORDER_SETTLEING(1,"可结算"),
		ORDER_SETTLEED(2,"已结算"),
		MAN_LOCK(3,"人工锁定"),
		WAIT_VERIFY(6,"待审核");
		
		Integer code;
		String message;
		
		private OrderSettleStatusEnum(Integer code, String message) {
			this.code = code;
			
			this.message = message;
		}
		
		public Integer getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static Integer getCode(Integer code) {
			
			OrderSettleStatusEnum[] statusEnums=values();
			
			for(OrderSettleStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(Integer code) {
			
			OrderSettleStatusEnum[] statusEnums=values();
			
			for(OrderSettleStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		
	}
	 
	/**
	 * 账户余额：变更类型，1支付，2提现
	 * @author zhenling
	 *
	 */
	public enum BankChangeTypeEnum{
	 
		PAY(1,"支付"),
		CASH(2,"提现");
		
		int code;
		String message;
		
		private BankChangeTypeEnum(int code, String message) {
			this.code = code;
			
			this.message = message;
		}
		
		public int getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static Integer getCode(int code) {
			
			BankChangeTypeEnum[] statusEnums=values();
			
			for(BankChangeTypeEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(int code) {
			
			BankChangeTypeEnum[] statusEnums=values();
			
			for(BankChangeTypeEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		
	}
	/**
	 * 账户余额:变更标识 0增加1减少
	 * @author zhenling
	 *
	 */
	public enum BankIsReduceEnum{
		
		ADD(0,"增加"),
		LESS(1,"减少");
		
		int code;
		String message;
		
		private BankIsReduceEnum(int code, String message) {
			this.code = code;
			
			this.message = message;
		}
		
		public int getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static Integer getCode(int code) {
			
			BankIsReduceEnum[] statusEnums=values();
			
			for(BankIsReduceEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(int code) {
			
			BankIsReduceEnum[] statusEnums=values();
			
			for(BankIsReduceEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		
	}
	/**
	 * 财务任务结算审核状态
	 * @author zhenling
	 *
	 */
	public enum TaskSettleAuditStatusEnum{
		
		AUDITSTATUS_WAIT(0,"待审核"),
		AUDITSTATUS_PASS(1,"审核通过"),
		AUDITSTATUS_QUESTIONNAIRE(2,"审核异常");
		
		int code;
		String message;
		
		private TaskSettleAuditStatusEnum(int code, String message) {
			this.code = code;
			
			this.message = message;
		}
		
		public int getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
		
		public static Integer getCode(int code) {
			
			TaskSettleAuditStatusEnum[] statusEnums=values();
			
			for(TaskSettleAuditStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getCode();
				}
				
			}
			
			return null;
			
		}
		
		public static String getMessage(int code) {
			
			TaskSettleAuditStatusEnum[] statusEnums=values();
			
			for(TaskSettleAuditStatusEnum statusEnum:statusEnums) {
				
				if(statusEnum.getCode()==code) {
					
					return statusEnum.getMessage();
				}
				
			}
			
			return null;
			
		}
		
	}
	 
	
	
}
