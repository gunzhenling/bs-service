package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.BillRefundLog;
import com.bs.payment.modules.trade.entity.BillRefundLogCach;

/**
 * 账单退款缓存记录
 * 
 * @author zhenling
 */
@Mapper
@Repository
public interface BillRefundLogCachMapper {
	
	int save(BillRefundLogCach billRefundLogCach);
	
	int saveBatch(@Param(value="list") List<BillRefundLog> billRefundLogs);
	/**
	 * 删除
	 * @param refundNo
	 * @param payType
	 * @param appId
	 * @return
	 */
	int delete(@Param(value="refundNo")String  refundNo,@Param(value="payType")String  payType,@Param(value="appId")String  appId);
	/**
	 * 删除
	 * @param refundNo
	 * @param payType
	 * @param appId
	 * @return
	 */
	int deleteBatch(@Param(value="list")List<BillRefundLogCach> billRefundLogCachs);
	/**
	 * 获取单条记录
	 * @param refundNo
	 * @param payType
	 * @param appId
	 * @return
	 */
	BillRefundLogCach findByRefundNoAndPayTypeAndAppId(@Param(value="refundNo")String  refundNo,@Param(value="payType")String  payType,@Param(value="appId")String  appId);
	/**
	 * 获取列表
	 * @param payType
	 * @param appId
	 * @return
	 */
	List<BillRefundLogCach> findListByPayTypeAndAppId(@Param(value="payType")String  payType,@Param(value="appId")String  appId);
	/**
	 * 获取列表超过三天的数据
	 * @param payType
	 * @param appId
	 * @return
	 */
	List<BillRefundLogCach> findListMoreThanThreeDay(@Param(value="payType")String  payType,@Param(value="appId")String  appId);

}
