package com.bs.payment.common.constans;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * 描述： 
 * 
 * <pre>
 * 可用余额流水变更类型
 * </pre>
 * 
 * @author wendy
 * @version: 0.0.1  2019年5月9日
 *
 */
public enum BankAvailableChangeEnum {

	PAY(1, 0, "支付充值"), 
	CASH_OUT(2, 1, "提现,手续费¥{}");  

	private int type;
	private int isReduce; // 0增加，1减少,2 不增加不减少
	private String desc;

	private BankAvailableChangeEnum(int type, int isReduce, String desc) {
		this.type = type;
		this.isReduce = isReduce;
		this.desc = desc;
	}

	public static String getDesc(Integer code) {
		
		BankAvailableChangeEnum[]  changeEnums=values();
		
		for(BankAvailableChangeEnum changeEnum:changeEnums) {
			
			if(changeEnum.getType()==code) {
				
				return changeEnum.getDesc();
			}
			
		}
		
		return null;
		
	}
	
	public String replacDesc(Object... objects) {
		String _text = desc;
		for (Object obj : objects) {
			if (null != obj) {
				_text = StringUtils.replaceOnce(desc, "{}", obj.toString());
			}
		}
		return _text;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsReduce() {
		return isReduce;
	}

	public void setIsReduce(int isReduce) {
		this.isReduce = isReduce;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


}
