package com.bs.payment.modules.trade.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.RefundNotifyLog;

/**
 * 退款回调日志记录
 * 
 * @author fanhang
 *
 */
@Mapper
@Repository
public interface RefundNotifyLogMapper {

	/**
	 * 保存支付回调记录
	 * @param log
	 * @return
	 */
	int save(RefundNotifyLog log);
	
	/**
	 * 模糊查询订单号
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	List<RefundNotifyLog> findByOrderNoLike(@Param("orderNo") String orderNo, @Param("payType") String payType);
	
	/**
	 * 根据退款单号获取退款回调记录
	 * @param refundNo
	 * @param payType
	 * @return
	 */
	RefundNotifyLog findByRefundNo(@Param("refundNo") String refundNo, @Param("payType") String payType);
	
	/**
	 * 根据订单号获取所有退款回调记录
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	List<RefundNotifyLog> findByOrderNo(@Param("orderNo") String orderNo, @Param("payType") String payType);
	
	List<RefundNotifyLog> findBetweenDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
