package com.bs.payment.util;

/**
 * 常量
 * @author zhenling
 *@time 20190509
 */
public class PayContants {
	
//	对账状态
	public static enum BillStatusEnum{
		
		NORMAL("正常",0),REEOR("异常",1),FIX("已调整",2);
		
		private String name;
		private int index;
		
		private BillStatusEnum(String name,int index) {
			this.name=name;
			this.index=index;
			
		}
		
		public static  boolean validStatus(int index) {
			
			for(BillStatusEnum bs:BillStatusEnum.values()) {
				if(bs.index==index) {
					return true;
				}
			}
			return false;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
	}
	
//	支付类别
	public static enum PayTypeEnum{
		
		ALIPay("支付宝","alipay"),WX("微信","wx");
		
		private String name;
		private String value;
		
		private PayTypeEnum(String name,String value){
			this.name=name;
			this.value=value;		
		}
		
		public static boolean validPayTypeExist(String value) {
			for(PayTypeEnum pt:PayTypeEnum.values()) {
				
				if(pt.getValue().equals(value)) {
					return true;
				}
			}
			return false;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}
	


}
