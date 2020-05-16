package com.bs.payment.modules.trade.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bs.payment.common.ZcResult;
import com.bs.payment.modules.trade.entity.OrderInfoEntity;
import com.bs.payment.modules.trade.entity.UserShopCardEntity;
import com.bs.payment.modules.trade.service.OrderService;
import com.bs.payment.modules.trade.service.UserShopCardService;
import com.bs.payment.modules.trade.vo.OrderCommitReqVO;
import com.bs.payment.modules.trade.vo.OrderCommitRespVO;
import com.bs.payment.modules.trade.vo.OrderPayReqVO;
import com.bs.payment.modules.trade.vo.ShopCommitReqVO;
import com.bs.payment.modules.trade.vo.UpdateShipStatusVO;

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
	public ZcResult<OrderCommitRespVO> commit(@Valid@RequestBody OrderCommitReqVO req) throws Exception {
		
		log.info("Order-commit-info: request  req={}",JSON.toJSONString(req));
		
		OrderCommitRespVO commit = orderService.commit(req);
		 
		return ZcResult.ok(commit);
	}
	
	
	@PostMapping("/pay")
	@ApiOperation(value = "订单支付")
	public ZcResult<String> pay(@Valid@RequestBody OrderPayReqVO req) throws Exception {
		
		log.info("Order-pay-info: request  req={}",JSON.toJSONString(req));
		
		 String  result = orderService.pay(req);
		 
		return ZcResult.ok(result);
	}
	
	@PostMapping("/update/shipStatus")
	@ApiOperation(value = "更新订单发货状态")
	public ZcResult<String> updateShipStatus(@Valid@RequestBody UpdateShipStatusVO req ) throws Exception {
		
		log.info("Order-updateShipStatus-info: request  req={}",JSON.toJSONString(req));
		
		 String updateShipStatus = orderService.updateShipStatus(req);
		 
		return ZcResult.ok(updateShipStatus);
	}
	 
	@GetMapping("/get/list")
	@ApiOperation(value = "获取订单列表")
	public ZcResult<ZcPageResult<OrderInfoEntity>> bgGetList(@RequestParam(value="order_no",required=false) String orderNo,
			@RequestParam(value="user_id",required=false) Long userId,
			@RequestParam(value="made_type",required=false) Integer madeType,
			@RequestParam(value="pay_status",required=false) Integer payStatus,
			@RequestParam(value="ship_status",required=false) Integer shipStatus,
			@RequestParam(value="limit",required=false,defaultValue="5") Integer limit,@RequestParam(value="offset",required=false,defaultValue="0") Integer offset) throws Exception {
		
		log.info("Order-bgGetList-info: request  orderNo={},madeType={},limit={},offset={}",orderNo,madeType,limit,offset);
		
		ZcPageResult<OrderInfoEntity> bgGetOrderList = orderService.bgGetOrderList(orderNo,madeType,payStatus,shipStatus,userId, limit, offset);
		
		return ZcResult.ok(bgGetOrderList);
	}
	/*
	@GetMapping("/get/list")
	@ApiOperation(value = "前端获取订单列表")
	public ZcResult<ZcPageResult<OrderInfoRespVO>> getList(@RequestParam(value="user_id",required=true) Long userId,
			@RequestParam(value="made_type",required=false) Integer madeType,
			@RequestParam(value="pay_status",required=false) Integer payStatus,
			@RequestParam(value="ship_status",required=false) Integer shipStatus,
			@RequestParam(value="limit",defaultValue="5") Integer limit,@RequestParam(value="offset",required=false,defaultValue="0") Integer offset) throws Exception {
		
		log.info("Order-getList-info: request  userId={},madeType={},limit={},offset={}",userId,madeType,limit,offset);
		
		ZcPageResult<OrderInfoRespVO> getOrderList = orderService.getOrderList(userId, madeType,payStatus,shipStatus,limit, offset);
		
		return ZcResult.ok(getOrderList);
	}*/
	
	@GetMapping("/get/shops")
	@ApiOperation(value = "获取购物车列表")
	public ZcResult<ZcPageResult<UserShopCardEntity>> getShops(@RequestParam(value="user_id",required=true) Long userId,
			@RequestParam(value="limit",defaultValue="5") Integer limit,@RequestParam(value="offset",required=false,defaultValue="0") Integer offset) throws Exception {
		
		log.info("Order-getList-info: request  userId={},limit={},offset={}",userId,limit,offset);
		 
		ZcPageResult<UserShopCardEntity> result = userShopCardService.getList(userId, limit, offset);
		
		return ZcResult.ok(result);
	}
	
	@PostMapping("/add/shops")
	@ApiOperation(value = "添加购物车")
	public ZcResult<String> addShops(@Valid@RequestBody ShopCommitReqVO req) throws Exception {
		
		log.info("Order-commit-info: request  req={}",JSON.toJSONString(req));
		
		String result = userShopCardService.add(req);
		 
		return ZcResult.ok(result);
	}
	
	
	@PostMapping("/cancle/shops/{id}")
	@ApiOperation(value = "删除购物车")
	public ZcResult<String> cancleShops(@PathVariable(value="id",required=true) Long id) throws Exception {
		
		log.info("Order-cancleShops-info: request  id={}",id);
		
		String result = userShopCardService.cancleShops(id);
		
		return ZcResult.ok(result);
	}
	
	@PostMapping("/update/shops/{id}/{gift_amount}")
	@ApiOperation(value = "修改购物车购买数量")
	public ZcResult<String> updateShopGiftAmount(@PathVariable(value="id",required=true) Long id,
			@PathVariable(value="gift_amount",required=true) Integer  giftAmount) throws Exception {
		
		log.info("Order-updateShopGiftAmount-info: request  id={}",id);
		
		String result = userShopCardService.updateShopGiftAmount(id, giftAmount);
		
		return ZcResult.ok(result);
	}

}
