package com.bs.payment.modules.trade.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.BillPayLog;

/**
 * 账单支付记录
 * 
 * @author fanhang
 */
@Mapper
@Repository
public interface BillPayLogMapper {

	/**
	 * 保存账单支付记录
	 * @param billPayLog
	 * @return
	 */
	int save(BillPayLog billPayLog);
	
	/**
	 * 根据订单号查询
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	BillPayLog findByOrderNo(@Param("appId") String appId,@Param("orderNo") String orderNo, @Param("payType") String payType);
	
	/**
	 * 查询账单日期内的支付记录
	 * @param billDate
	 * @param payType
	 * @return
	 */
	List<BillPayLog> findByBillDate(@Param("appId") String appId,@Param("billDate") String billDate, @Param("payType") String payType);
	
	List<BillPayLog> findInOrders(@Param("orders") Set<String> orders, @Param("payType") String payType);
	
	/**
	 * 按账单日期区间统计手续费
	 * @param appId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<BillPayLog> sumServiceCharge(@Param("appId") String appId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
