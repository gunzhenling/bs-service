package com.bs.payment.modules.trade.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.PayQueryResult;

/**
 * 支付查询结果
 * 
 * @author fanhang
 *
 */
@Mapper
@Repository
public interface PayQueryResultMapper {

	/**
	 * 保存退款查询结果
	 * @param queryResult
	 * @return
	 */
	int save(PayQueryResult queryResult);
	
	/**
	 * 根据订单号获取支付查询记录
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	PayQueryResult findByOrderNo(@Param("appId") String appId,@Param("orderNo") String orderNo, @Param("payType") String payType);
	
	/**
	 * 模糊查询订单号
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	List<PayQueryResult> findByOrderNoLike(@Param("orderNo") String orderNo, @Param("payType") String payType);
	
	List<PayQueryResult> findBetweenDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	List<PayQueryResult> findInOrders(@Param("orders") Set<String> orders, @Param("payTypes") Set<String> payTypes);
}
