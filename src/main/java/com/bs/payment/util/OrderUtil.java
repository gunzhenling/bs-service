package com.bs.payment.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.bs.payment.common.constans.Consts;

import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.time.ZcDateFormatUtil;
import xyz.nesting.common.time.ZcDateUtil;

/**
 * 生成单号
 * @author zhenling
 *
 */
@Component
@Slf4j
public class OrderUtil {
	
	/**
	 * 生成订单号和退款单号
	 * @param type
	 * @return
	 */
	public static String getOrder(String type) {
		 
		
		String orderNo="";
		if(Consts.OrderType.REFUND.equals(type)) {
			
//			refund+14+12
			orderNo = type + ZcDateFormatUtil.formatDate("yyyyMMddHHmmss", ZcDateUtil.today())
			+ RandomStringUtils.randomNumeric(12);
		}
		if(Consts.OrderType.PAY.equals(type)) {
			
//			14+18
			orderNo = ZcDateFormatUtil.formatDate("yyyyMMddHHmmss", ZcDateUtil.today())
			+ RandomStringUtils.randomNumeric(18);
		}
		if(Consts.OrderType.BANK_AVAIL.equals(type)) {
			
//			14+18
			orderNo = ZcDateFormatUtil.formatDate("yyyyMMddHHmmss", ZcDateUtil.today())
			+ RandomStringUtils.randomNumeric(18);
		}
		if(Consts.OrderType.CARRIER.equals(type)) {
			
//			14+18
			orderNo = ZcDateFormatUtil.formatDate("yyyyMMddHHmmss", ZcDateUtil.today())
					+ RandomStringUtils.randomNumeric(18);
		}
		if("billF".equals(type)) {
			
			String corporateBillNo = "F" + Consts.SELL_BILL_VERSION + ZcDateFormatUtil.formatDate("yyyyMMddHHmmss", ZcDateUtil.today())
						+ RandomStringUtils.randomNumeric(8);
			orderNo=corporateBillNo;
		}
		if("billV".equals(type)) {
			
			String corporateBillNo = "V" + Consts.SELL_BILL_VERSION + ZcDateFormatUtil.formatDate("yyyyMMddHHmmss", ZcDateUtil.today())
			+ RandomStringUtils.randomNumeric(8);
			orderNo=corporateBillNo;
		}
		
		return orderNo;
	}
	
	 
	/**
	 *  解析手机号码
	 * @param mobile  +86-15364839044
	 * @return  15364839044
	 */
	public static String getMobile(String mobile){
		
		if(mobile.contains("-")) {
			String[] split = mobile.split("-", mobile.length());
			mobile=split[1];
		}
		return mobile;
		
	}
	
	/**
	 * 判断任务截止时间是否到期
	 * @param endTime
	 * @return
	 */
	public static boolean validTaskEndtime(Date endTime){
		
		if(endTime==null){
			return false;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		String endTimeStr = formatter.format(endTime);
		
		Calendar calendar = Calendar.getInstance(); 
		Date nowTime = calendar.getTime();
		String nowTimeStr = formatter.format(nowTime);
		
		Date endDate = null;
		Date nowDate = null;
		try {
			
			  endDate = formatter.parse(endTimeStr);
			  nowDate = formatter.parse(nowTimeStr);
			
		} catch (ParseException e) {
			 
			e.printStackTrace();
			log.info("taskOrderUtils-validTaskEndtime-error: 解析日期error={}",e.getMessage());
		}
		
		if(endDate!=null&&nowDate!=null){
			
				if(endDate.getTime()<nowDate.getTime()){
					
					return true;
				}
			
		}
		
		return false;
	}
	
	/**
	 * 判断任务支付时间是否超过两个月
	 * payTime+2<=nowTime
	 * @param endTime
	 * @return
	 */
	public static boolean validTaskPayTimeExpired(Date payTime){
		
		if(payTime==null){
			return false;
		}
		
		Calendar calendar = Calendar.getInstance(); 
		calendar.add(Calendar.MONTH, -2);//当前时间往后倒退两个月
		Date nowLessTwoMouthTime = calendar.getTime();
		 
//	 payTime<=nowTime-2
		if(payTime.getTime()<=nowLessTwoMouthTime.getTime()){
			
			log.info("taskOrderUtils-validTaskPayTimeExpired-info:  任务支付时间过期超过两个月");
			return true;
		}
		
		return false;
	}
	
	 

}
