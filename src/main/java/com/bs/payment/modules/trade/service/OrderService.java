package com.bs.payment.modules.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.modules.trade.entity.OrderInfoEntity;
import com.bs.payment.modules.trade.vo.BgOrderInfoRespVO;
import com.bs.payment.modules.trade.vo.OrderCommitReqVO;
import com.bs.payment.modules.trade.vo.OrderCommitRespVO;
import com.bs.payment.modules.trade.vo.OrderInfoRespVO;
import com.bs.payment.modules.trade.vo.OrderPayReqVO;
import com.bs.payment.modules.trade.vo.UpdateShipStatusVO;

import xyz.nesting.common.message.ZcPageResult;

/**
 * 订单接口
 * @author zhenling
 *
 */
public interface OrderService extends IService<OrderInfoEntity> {
	

	/**
	 * 管理后台获取订单列表
	 * @param orderNo
	 * @param limit
	 * @param offset
	 * @return
	 */
	ZcPageResult<BgOrderInfoRespVO> bgGetOrderList(String orderNo,Integer limit ,Integer offset);
	/**
	 * 前端获取订单列表
	 * @param orderNo
	 * @param limit
	 * @param offset
	 * @return
	 */
	ZcPageResult<OrderInfoRespVO> getOrderList(Long userId,Integer limit ,Integer offset);
	
	/**
	 * 更新订单发货状态
	 * @param orderNo
	 * @return
	 */
	String updateShipStatus(UpdateShipStatusVO req);
	/**
	 * 提交订单
	 * @param orderNo
	 * @return
	 */
	OrderCommitRespVO commit(OrderCommitReqVO req) throws Exception;
	/**
	 * 订单支付
	 * @param orderNo
	 * @return
	 */
	String pay(OrderPayReqVO req) throws Exception;
}
