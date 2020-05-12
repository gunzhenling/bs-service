package com.bs.payment.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;
/**
 * 
 * @author zhenling
 *
 */
public class MoneyUtil {
	
	/**
	 * 金额分为元, 保留两位小数
	 * @param amount
	 * @return
	 */
	public static BigDecimal divide100(int amount) {
		return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 金额元转分, 忽略小数
	 * @param amount
	 * @return
	 */
	public  static int multiply100(String amount) {
		return (int) (NumberUtils.toDouble(amount) * 100);
	}

}
