package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.entity.OrderInfoEntity;
import com.bs.payment.modules.trade.vo.BgOrderInfoRespVO;
import com.bs.payment.modules.trade.vo.OrderInfoRespVO;

/**
 *  订单
 * 
 * @author gunzhenling
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfoEntity>{
	
	 
	Long getCount(@Param(value="orderNo")String orderNo,@Param(value="userId")Long userId);
	
	List<BgOrderInfoRespVO> getBgOrderList(@Param(value="orderNo")String orderNo
			,@Param(value="limit") Integer limit,@Param(value="offset") Integer offset);
	
	List<OrderInfoRespVO> getOrderList(@Param(value="userId")Long userId
			,@Param(value="limit") Integer limit,@Param(value="offset") Integer offset);

}
