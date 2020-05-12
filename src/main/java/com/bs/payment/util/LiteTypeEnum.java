package com.bs.payment.util;

/**
 * 小程序类型
 * @author zhenling
 *
 */
public enum  LiteTypeEnum {
	

//	type=douyin ，alipay

	DOU_YIN("douyin","抖音小程序"),
	ALIPAY("alipay","支付宝小程序");
	
	String code;
	String message;
	
	private LiteTypeEnum(String code, String message) {
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
		
		LiteTypeEnum[] statusEnums=values();
		
		for(LiteTypeEnum statusEnum:statusEnums) {
			
			if(statusEnum.getCode().equals(code)) {
				
				return statusEnum.getCode();
			}
			
		}
		
		return null;
		
	}
	
	public static String getMessage(String code) {
		
		LiteTypeEnum[]  statusEnums=values();
		
		for(LiteTypeEnum statusEnum:statusEnums) {
			
			if(statusEnum.getCode().equals(code)) {
				
				return statusEnum.getMessage();
			}
			
		}
		
		return null;
		
	}
	 
	


}
