package com.bs.payment.modules.trade.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.BillCompareBatch;

/**
 * 账单比对批次
 * 
 * @author fanhang
 */
@Mapper
@Repository
public interface BillCompareBatchMapper {

	/**
	 * 保存账单汇总记录
	 * @param compareBatch
	 * @return
	 */
	int create(BillCompareBatch compareBatch);
	
	/**
	 * 创建后修改批次的数量信息
	 * @param compareBatch
	 * @return
	 */
	int update(BillCompareBatch compareBatch);
	
	/**
	 * 递减未处理数量
	 * @param id
	 * @param updateTime
	 * @return
	 */
	int decrementUnhandleMistakeCount(@Param("id") Integer id, @Param("createTime") Date updateTime);

	/**
	 * 按批次 ID 查询批次信息
	 * @param id
	 * @return
	 */
	BillCompareBatch findById(@Param("id") Integer id);
	
	/**
	 * 按批次名称查询批次信息
	 * @param name
	 * @return
	 */
	BillCompareBatch findByName(@Param("name") String name);
	/**
	 * 按批次appId、名称查询 批次信息
	 * @param appId
	 * @param name
	 * @return
	 */
	BillCompareBatch findByappIdAndName(@Param("appId") String appId,@Param("name") String name);
	
	/**
	 * 按账单日期和类型搜索
	 * @param billDate
	 * @param payType
	 * @return
	 */
	BillCompareBatch findByBillDate(@Param("billDate") String billDate, @Param("payType") String payType,@Param("appId") String appId);
	/**
	 * 按条件获取对账批次列表
	 * @param params
	 * @return
	 */
	List<BillCompareBatch> compareBatchList(@Param("params") Map<String, Object> params);
	
	/**
	 * 按条件获取对账批次列表数量
	 * @param params
	 * @return
	 */
	int compareBatchCount(@Param("params") Map<String, Object> params);
	
}
