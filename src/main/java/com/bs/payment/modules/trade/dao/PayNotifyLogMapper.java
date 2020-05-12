package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.PayNotifyLog;

/**
 * 支付回调日志记录
 * 
 * @author fanhang
 *
 */
@Mapper
@Repository
public interface PayNotifyLogMapper {

	/**
	 * 保存支付回调记录
	 * @param log
	 * @return
	 */
	int save(PayNotifyLog log);
	
	/**
	 * 根据订单号查询支付回调记录
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	PayNotifyLog findByOrderNo(@Param("orderNo") String orderNo, @Param("payType") String payType);
	
	/**
	 * 根据订单号模糊查询支付回调记录
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	List<PayNotifyLog> findByOrderNoLike(@Param("orderNo") String orderNo, @Param("payType") String payType);
}
