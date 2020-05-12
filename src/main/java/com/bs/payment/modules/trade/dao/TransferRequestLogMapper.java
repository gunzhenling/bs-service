package com.bs.payment.modules.trade.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.TransferRequestLog;

/**
 * 转账请求日志记录
 * 
 * @author fanhang
 *
 */
@Mapper
@Repository
public interface TransferRequestLogMapper {

	/**
	 * 保存转账请求记录
	 * @param log
	 * @return
	 */
	int save(TransferRequestLog log);
	
	/**
	 * 根据转账单号获取转账记录
	 * @param transferNo
	 * @return
	 */
	TransferRequestLog findByTransferNo(@Param("transferNo") String transferNo);

	List<TransferRequestLog> findInOrders(@Param("orders") Set<String> orders, @Param("payType") String payType);
	
	/**
	 * 在时间区间内查找请求记录
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<TransferRequestLog> findBetweenDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("payType") String payType);
}
