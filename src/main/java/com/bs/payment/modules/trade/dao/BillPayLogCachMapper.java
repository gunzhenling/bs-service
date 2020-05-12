package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.BillPayLog;
import com.bs.payment.modules.trade.entity.BillPayLogCach;

/**
 * 账单支付缓存
 * 
 * @author zhenling
 */
@Mapper
@Repository
public interface BillPayLogCachMapper {
	
	int save(BillPayLogCach billPayLogCach);
	
	int saveBatch(@Param(value="list") List<BillPayLog> list);
	 
	int deleteBatch(@Param(value="list") List<BillPayLogCach> blcList);
	
	/**
	 * 删除
	 * @param orderNo
	 * @param payType
	 * @param appId
	 * @return
	 */
	
	int delete(@Param(value="orderNo")String orderNo,@Param(value="payType")String payType,@Param(value="appId")String appId);
	/**
	 * 获取单条数据
	 * @param orderNo
	 * @param payType
	 * @param appId
	 * @return
	 */
	BillPayLogCach findByOrderNo(@Param(value="orderNo")String orderNo,@Param(value="payType")String payType,@Param(value="appId")String appId);
	/**
	 * 获取列表
	 * @param payType
	 * @param appId
	 * @return
	 */
	List<BillPayLogCach> findListByPayTypeAndAppId(@Param(value="payType")String payType,@Param(value="appId")String appId);
	/**
	 * 获取小于当天时间超过三天的数据列表
	 * @param payType
	 * @param appId
	 * @return
	 */
	List<BillPayLogCach> findListMoreThanThreeDay(@Param(value="payType")String payType,@Param(value="appId")String appId);
	

}
