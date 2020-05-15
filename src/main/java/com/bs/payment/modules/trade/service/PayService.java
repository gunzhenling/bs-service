package com.bs.payment.modules.trade.service;

import com.bs.payment.modules.trade.dto.PayDto;
/**
 * 支付接口
 * @author zhenling
 *
 */
public interface PayService {
	
	/**
	 * 支付
	 * @param payDto
	 * @return
	 */
	boolean pay(PayDto payDto);

}
