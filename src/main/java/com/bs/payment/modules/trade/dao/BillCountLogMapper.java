package com.bs.payment.modules.trade.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.BillCountLog;

/**
 * 账单汇总记录
 * 
 * @author fanhang
 */
@Mapper
@Repository
public interface BillCountLogMapper {

	/**
	 * 保存账单汇总记录
	 * @param billCountLog
	 * @return
	 */
	int save(BillCountLog billCountLog);

	/**
	 * 查询账单汇总记录
	 * @param appId
	 * @param payType
	 * @param billDate
	 * @return
	 */
	BillCountLog find(@Param("appId") String appId, @Param("payType") String payType, @Param("billDate") String billDate,@Param("merchantId")  String merchantId);
}
