package com.bs.payment.modules.trade.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.PayRequestLog;

/**
 * 支付请求日志记录
 * 
 * @author fanhang
 */
@Mapper
@Repository
public interface PayRequestLogMapper {

	/**
	 * 保存支付请求记录
	 * @param log
	 * @return
	 */
	int save(PayRequestLog log);
	
	/**
	 * 保存支付请求的响应数据
	 * @param orderNo
	 * @param payType
	 * @param response
	 * @return
	 */
	int saveResponse(@Param("id") Integer id, @Param("response") Object response);
	/**
	 * 保存extra
	 * @param orderNo
	 * @param payType
	 * @param response
	 * @return
	 */
	int saveExtra(@Param("id") Integer id, @Param("extra") String extra);
	
	/**
	 * 获取支付响应数据
	 * @param id
	 * @return
	 */
	PayRequestLog getResponse(@Param("id") int id);

	/**
	 * 根据订单号获取单个请求数据
	 * @param orderNo
	 * @param payType
	 * @param appId
	 * @return
	 */
	PayRequestLog findByOrderNo(@Param("orderNo") String orderNo, @Param("payType") String payType, @Param("appId") String appId);
	
//	20190505 gunzl 支持小程序支付 beg
	/**
	 * 根据appid,订单号获取单个请求数据
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	PayRequestLog findByAppIdAndOrderNo(@Param("appId") String appId, @Param("orderNo") String orderNo);
//	20190505 gunzl 支持小程序支付 end
	/**
	 * 模糊查询订单号
	 * @param orderNo
	 * @param payType
	 * @return
	 */
	List<PayRequestLog> findByOrderNoLike(@Param("orderNo") String orderNo, @Param("payType") String payType);
	
	/**
	 * 删除日志记录
	 * @param id
	 */
	void delete(@Param("id") Integer id);

	List<PayRequestLog> findInOrders(@Param("orders") Set<String> orders, @Param("payType") String payType);
	
	/**
	 * 在时间区间内查找支付请求记录
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<PayRequestLog> findBetweenDate(@Param("appId") String appId,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("payType") String payType);
	
	/**
	 * 在时间区间内查找支付请求记录-关联支付回调表
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<PayRequestLog> findBetweenDateInnerJoinNotify(@Param("appId") String appId,@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("payType") String payType);
	
	/**
	 * 在时间区间内查找未查询支付结果的记录
	 * @param startDate
	 * @param endDate
	 * @param payType
	 * @return
	 */
	List<PayRequestLog> findUnqueryBetweenDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("payType") String payType);
}
