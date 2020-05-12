package com.bs.payment.modules.trade.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.RefundQueryResult;

/**
 * 退款查询结果记录
 * 
 * @author fanhang
 *
 */
@Mapper
@Repository
public interface RefundQueryResultMapper {

	/**
	 * 保存退款查询结果
	 * @param queryResult
	 * @return
	 */
	int save(RefundQueryResult queryResult);
	
	/**
	 * 模糊查询订单号
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	List<RefundQueryResult> findByOrderNoLike(@Param("orderNo") String orderNo, @Param("payType") String payType);
	
	/**
	 * 根据退款单号获取所有退款查询记录
	 * @param refundNo
	 * @param payType
	 * @return
	 */
	RefundQueryResult findByRefundNo(@Param("refundNo") String refundNo, @Param("payType") String payType);
	
	List<RefundQueryResult> findInOrders(@Param("orders") Set<String> orders, @Param("payTypes") Set<String> payTypes);
	
	List<RefundQueryResult> findInRefunds(@Param("refunds") Set<String> refunds, @Param("payTypes") Set<String> payTypes);
	
}
