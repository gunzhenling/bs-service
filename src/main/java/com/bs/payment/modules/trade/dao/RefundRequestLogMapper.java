package com.bs.payment.modules.trade.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.RefundRequestLog;

/**
 * 退款请求日志记录
 * 
 * @author fanhang
 *
 */
@Mapper
@Repository
public interface RefundRequestLogMapper {

	/**
	 * 保存退款请求记录
	 * @param log
	 * @return
	 */
	int save(RefundRequestLog log);
	
	/**
	 * 保存退款请求的响应数据
	 * @param refundNo
	 * @param payType
	 * @param response
	 * @return
	 */
	int saveResponse(@Param("id") Integer id, @Param("response") Object response);
	
	/**
	 * 模糊查询订单号
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	List<RefundRequestLog> findByOrderNoLike(@Param("orderNo") String orderNo, @Param("payType") String payType);
	
	/**
	 * 根据退款单号获取所有退款请求记录
	 * @param refundNo
	 * @param payType
	 * @return
	 */
	RefundRequestLog findByRefundNo(@Param("refundNo") String refundNo, @Param("payType") String payType, @Param("appId") String appId);

	List<RefundRequestLog> findInOrders(@Param("orders") Set<String> orders, @Param("payType") String payType);
	
	List<RefundRequestLog> findInRefunds(@Param("refundNoSet") Set<String> refundNoSet, @Param("payType") String payType);
	
	/**
	 * 在时间区间内查找退款请求记录
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<RefundRequestLog> findBetweenDate(@Param("appId") String appId,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("payType") String payType);
	
	/**
	 * 在时间区间内查找退款请求记录-关联退款回调通知表
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<RefundRequestLog> findBetweenDateInnerJoinNotify(@Param("appId") String appId,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("payType") String payType);
	
	/**
	 * 在时间区间内查找未查询退款结果的记录
	 * @param startDate
	 * @param endDate
	 * @param payType
	 * @return
	 */
	List<RefundRequestLog> findUnqueryBetweenDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("payType") String payType);
	
	
}
