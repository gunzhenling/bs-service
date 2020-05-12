package com.bs.payment.modules.trade.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.BillParseLog;

/**
 * 账单解析日志记录
 * 
 * @author fanhang
 */
@Mapper
@Repository
public interface BillParseLogMapper {

	/**
	 * 保存账单解析日志
	 * @param billParseLog
	 * @return
	 */
	int save(BillParseLog billParseLog);

	/**
	 * 查询账单解析日志
	 * @param appId
	 * @param payType
	 * @param billDate
	 * @return
	 */
	BillParseLog find(@Param("appId") String appId, @Param("payType") String payType, @Param("billDate") String billDate);
}
