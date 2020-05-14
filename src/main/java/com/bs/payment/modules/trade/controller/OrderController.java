package com.bs.payment.modules.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bs.payment.common.ZcResult;
import com.bs.payment.modules.trade.dto.UserShopCardDto;
import com.bs.payment.modules.trade.service.OrderService;
import com.bs.payment.modules.trade.service.UserShopCardService;
import com.bs.payment.modules.trade.vo.BgOrderInfoRespVO;
import com.bs.payment.modules.trade.vo.OrderCommitReqVO;
import com.bs.payment.modules.trade.vo.OrderCommitRespVO;
import com.bs.payment.modules.trade.vo.OrderInfoRespVO;
import com.bs.payment.modules.trade.vo.ShopCommitReqVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;

/**
 * 订单相关接口
 * 
 * @author gunzhenling
 */
@Slf4j
@RestController
@RequestMapping("/bs/order")
@Api(tags = "订单相关接口")
public class OrderController {
	

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserShopCardService userShopCardService;
	
	@PostMapping("/commit")
	@ApiOperation(value = "提交订单")
	public ZcResult<OrderCommitRespVO> commit(@RequestBody OrderCommitReqVO req) throws Exception {
		
		log.info("Order-commit-info: request  req={}",JSON.toJSONString(req));
		
		OrderCommitRespVO commit = orderService.commit(req);
		 
		return ZcResult.ok(commit);
	}
	
	
	@PostMapping("/pay")
	@ApiOperation(value = "订单支付")
	public ZcResult<String> pay(@RequestParam(value="order_no",required=true) String orderNo,
			@RequestParam(value="pay_channel",required=true) String payChannel ) throws Exception {
		
		log.info("Order-pay-info: request  orderNo={},payChannel={}",orderNo,payChannel);
		
		 String  result = orderService.pay(orderNo, payChannel);
		 
		return ZcResult.ok(result);
	}
	
	@PostMapping("/update/shipStatus")
	@ApiOperation(value = "更新订单发货状态")
	public ZcResult<String> updateShipStatus(@RequestParam(value="order_no",required=true) String orderNo,
			@RequestParam(value="ship_status",required=true) Integer shipStatus ) throws Exception {
		
		log.info("Order-updateShipStatus-info: request  orderNo={},shipStatus={}",orderNo,shipStatus);
		
		 String updateShipStatus = orderService.updateShipStatus(orderNo, shipStatus);
		 
		return ZcResult.ok(updateShipStatus);
	}
	 
	@GetMapping("/bg/get/list")
	@ApiOperation(value = "管理后台获取订单列表")
	public ZcResult<ZcPageResult<BgOrderInfoRespVO>> bgGetList(@RequestParam("order_no") String orderNo,@RequestParam("limit") Integer limit,@RequestParam("offset") Integer offset) throws Exception {
		
		log.info("Order-bgGetList-info: request  orderNo={},limit={},offset={}",orderNo,limit,offset);
		
		ZcPageResult<BgOrderInfoRespVO> bgGetOrderList = orderService.bgGetOrderList(orderNo, limit, offset);
		
		return ZcResult.ok(bgGetOrderList);
	}
	
	@GetMapping("/get/list")
	@ApiOperation(value = "前端获取订单列表")
	public ZcResult<ZcPageResult<OrderInfoRespVO>> getList(@RequestParam("user_id") Long userId,@RequestParam("limit") Integer limit,@RequestParam("offset") Integer offset) throws Exception {
		
		log.info("Order-getList-info: request  userId={},limit={},offset={}",userId,limit,offset);
		
		ZcPageResult<OrderInfoRespVO> getOrderList = orderService.getOrderList(userId, limit, offset);
		
		return ZcResult.ok(getOrderList);
	}
	
	@GetMapping("/get/shops")
	@ApiOperation(value = "获取购物车列表")
	public ZcResult<ZcPageResult<UserShopCardDto>> getShops(@RequestParam("user_id") Long userId,@RequestParam("limit") Integer limit,@RequestParam("offset") Integer offset) throws Exception {
		
		log.info("Order-getList-info: request  userId={},limit={},offset={}",userId,limit,offset);
		 
		ZcPageResult<UserShopCardDto> result = userShopCardService.getList(userId, limit, offset);
		
		return ZcResult.ok(result);
	}
	
	@PostMapping("/add/shops")
	@ApiOperation(value = "添加购物车")
	public ZcResult<String> addShops(@RequestBody ShopCommitReqVO req) throws Exception {
		
		log.info("Order-commit-info: request  req={}",JSON.toJSONString(req));
		
		String result = userShopCardService.add(req);
		 
		return ZcResult.ok(result);
	}

}
