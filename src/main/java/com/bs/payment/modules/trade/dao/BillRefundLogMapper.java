package com.bs.payment.modules.trade.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.BillRefundLog;

/**
 * 账单退款记录
 * 
 * @author fanhang
 */
@Mapper
@Repository
public interface BillRefundLogMapper {

	/**
	 * 保存退款查询结果
	 * @param billRefundLog
	 * @return
	 */
	int save(BillRefundLog billRefundLog);
	
	/**
	 * 根据退款单号查询退款记录
	 * @param refundNo
	 * @param payType
	 * @return
	 */
	BillRefundLog findByRefundNo(@Param("appId") String appId,@Param("refundNo") String refundNo, @Param("payType") String payType);
	
	/**
	 * 查询订单下的退款记录
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	List<BillRefundLog> findByOrderNo(@Param("appId") String appId,@Param("orderNo") String orderNo, @Param("payType") String payType);
	
	/**
	 * 查询账单日期内的退款记录
	 * @param billDate
	 * @param payType
	 * @return
	 */
	List<BillRefundLog> findByBillDate(@Param("appId") String appId,@Param("billDate") String billDate, @Param("payType") String payType);
	
	List<BillRefundLog> findInOrders(@Param("orders") Set<String> orders, @Param("payType") String payType);
	
	/**
	 * 按订单编号统计退款手续费
	 * @param orders
	 * @param payType
	 * @return
	 */
	List<BillRefundLog> sumOrderServiceCharge(@Param("orders") Set<String> orders, @Param("payType") String payType);
	
	/**
	 * 按账单日期区间统计手续费
	 * @param appId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<BillRefundLog> sumServiceCharge(@Param("appId") String appId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
