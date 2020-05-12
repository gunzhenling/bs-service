package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.payment.modules.trade.entity.BillCompareDetail;

/**
 * 账单比对明细
 * 
 * @author fanhang
 */
@Mapper
@Repository
public interface BillCompareDetailMapper {

	/**
	 * 批量保存账单比对明细
	 * @param billCountLog
	 * @return
	 */
	int batchSave(@Param("batchId") Integer batchId, @Param("list") List<BillCompareDetail> list);

	/**
	 * 查找比对明细
	 * @param batchId
	 * @return
	 */
	List<BillCompareDetail> findByBatchId(@Param("batchId") Integer batchId);
}
