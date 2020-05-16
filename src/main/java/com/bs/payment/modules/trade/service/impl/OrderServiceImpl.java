package com.bs.payment.modules.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.common.constans.Consts;
import com.bs.payment.common.constans.EnumConstants;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.dao.OrderInfoMapper;
import com.bs.payment.modules.trade.dto.PayDto;
import com.bs.payment.modules.trade.dto.UserAddressDto;
import com.bs.payment.modules.trade.entity.BsGiftInfoEntity;
import com.bs.payment.modules.trade.entity.OrderInfoEntity;
import com.bs.payment.modules.trade.entity.UserAddressEntity;
import com.bs.payment.modules.trade.service.GiftInfoService;
import com.bs.payment.modules.trade.service.OrderService;
import com.bs.payment.modules.trade.service.PayService;
import com.bs.payment.modules.trade.service.UserAddressService;
import com.bs.payment.modules.trade.vo.BgOrderInfoRespVO;
import com.bs.payment.modules.trade.vo.OrderCommitReqVO;
import com.bs.payment.modules.trade.vo.OrderCommitRespVO;
import com.bs.payment.modules.trade.vo.OrderInfoRespVO;
import com.bs.payment.modules.trade.vo.OrderPayReqVO;
import com.bs.payment.modules.trade.vo.UpdateShipStatusVO;
import com.bs.payment.util.BeanKit;
import com.bs.payment.util.DateKit;
import com.bs.payment.util.OrderUtil;
import com.bs.payment.util.QueryBuilder;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderInfoMapper , OrderInfoEntity> implements OrderService {
	
	
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private GiftInfoService giftInfoService;
	@Autowired
	private UserAddressService userAddressService;
	@Autowired
	private PayService payService;
	
	@Override
	public ZcPageResult<BgOrderInfoRespVO> bgGetOrderList(String orderNo, Integer limit, Integer offset) {
		

		ZcPageResult<BgOrderInfoRespVO> page = new ZcPageResult<BgOrderInfoRespVO>();
		List<BgOrderInfoRespVO> bgOrderList = null;
		
		Long userId=null;
		Long count = orderInfoMapper.getCount(orderNo, userId);
		
		if(count==null) {
			
			bgOrderList = Lists.newArrayList();
			page.setData(bgOrderList);
			page.setTotal(0);
			
			return page;
		}
		
		bgOrderList = orderInfoMapper.getBgOrderList(orderNo, limit, offset);
		
		page.setData(bgOrderList);
		page.setTotal(count);
		
		
		return page;
	}

	@Override
	public ZcPageResult<OrderInfoRespVO> getOrderList(Long userId, Integer limit, Integer offset) {
		 
		ZcPageResult<OrderInfoRespVO> page = new ZcPageResult<OrderInfoRespVO>();
		List<OrderInfoRespVO> orderList = null;
		
		String orderNo=null;
		Long count = orderInfoMapper.getCount(orderNo, userId);
		
		if(count==null) {
			
			orderList = Lists.newArrayList();
			page.setData(orderList);
			page.setTotal(0);
			
			return page;
		}
		
		orderList = orderInfoMapper.getOrderList(userId, limit, offset);
		
		page.setData(orderList);
		page.setTotal(count);
		
		
		return page;
	}

	@Override
	public String updateShipStatus(UpdateShipStatusVO req) {
		
		String orderNo = req.getOrderNo();
		Integer shipStatus = req.getShipStatus();
		
		OrderInfoEntity entity = orderInfoMapper.selectOne(QueryBuilder.where("order_no", orderNo));
		if(entity==null) {
			
			String message="订单不存在";
			log.warn("order-updateShipStatus-warn: orderNo={}, shipStatus={} ,message={}",orderNo,shipStatus,message);
			throw new BusinessException(message);
			
		}
		
		Date date = new Date();
		entity.setShipStatus(shipStatus);
		entity.setShipTime(date);
		entity.setUpdateTime(date);
		
		orderInfoMapper.updateById(entity);
	 
		log.info("order-updateShipStatus-info: orderNo={}, shipStatus={}  success",orderNo,shipStatus);
		 
		return Consts.SUCCESS;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public String pay(OrderPayReqVO req) throws Exception{
		
		String orderNo = req.getOrderNo();
		String payChannel = req.getPayChannel();
		
		OrderInfoEntity entity = orderInfoMapper.selectOne(QueryBuilder.where("order_no", orderNo));
		if(entity==null) {
			
			String message="订单不存在";
			log.warn("order-pay-warn: orderNo={}, payChannel={} ,message={}",orderNo,payChannel,message);
			throw new BusinessException(message);
			
		}
		
		Integer payStatusPaySuccess = EnumConstants.PayStatusEnum.ORDER_PAY_SUCCESS.getCode();
		Integer payStatus = entity.getPayStatus();
		if(payStatus>=payStatusPaySuccess) {
			
			String message="订单已支付,请勿重复支付!";
			log.warn("order-pay-warn: orderNo={}, payChannel={} ,message={}",orderNo,payChannel,message);
			throw new BusinessException(message);
			
		}
		
		String merchantNo="测试商户号";
		
		Date date = new Date();
		entity.setPayStatus(payStatusPaySuccess);
		entity.setPayChannel(payChannel);
		entity.setMerchantNo(merchantNo);
		entity.setSettleStatus(EnumConstants.SettleStatusEnum.WAIT_AUDIT.getCode());
		entity.setShipStatus(EnumConstants.ShipStatusEnum.WAIT_SHIP.getCode());
		entity.setUpdateTime(date);
		entity.setPayTime(date);
		
		orderInfoMapper.updateById(entity);

		//		TODO 扣除用户金额
		PayDto payDto = new PayDto();
		BigDecimal payMoney = entity.getBuyerPayAmount();
		
		payDto.setOrderNo(orderNo);
		payDto.setPayChannel(payChannel);
		payDto.setPayMoney(payMoney);
		payDto.setUserId(entity.getUserId());
		
		payService.pay(payDto);
		
		log.info("order-pay-info: orderNo={}, payStatus={},payChannel={}  success",orderNo,payStatus,payChannel);
		 
		return Consts.SUCCESS;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public OrderCommitRespVO commit(OrderCommitReqVO req) throws Exception{
		
		BigDecimal buyerPayAmount = req.getBuyerPayAmount();
		String customMade = JSON.toJSONString(req.getCustomMade());
		Integer giftAmount = req.getGiftAmount();
		Integer giftCode = req.getGiftCode();
		BigDecimal sellIncome = req.getSellIncome();
		String specification = JSON.toJSONString(req.getSpecification());
		Long userAddressId = req.getUserAddressId();
		Long userId = req.getUserId();
		
		Integer waitPayStatus = EnumConstants.PayStatusEnum.ORDER_PAY_PENDING.getCode();
		OrderInfoEntity orderInfoEntity = orderInfoMapper.selectOne(QueryBuilder.where("user_id", userId,"pay_status",waitPayStatus));
		if(null!=orderInfoEntity) {
			String message="您有未支付的订单，请先进行支付!";
			log.warn("order-commit-warn: giftCode={} ,message={},waitPayStatus={},userId={}",giftCode,message,waitPayStatus,userId);
			throw new BusinessException(message);
		}
		
		BsGiftInfoEntity giftInfoEntity = giftInfoService.getOne(QueryBuilder.where("gift_code", giftCode));
		if(null==giftInfoEntity) {
			String message="该礼品不存在";
			log.warn("order-commit-warn: giftCode={} ,message={}",giftCode,message);
			throw new BusinessException(message);
		}
		UserAddressEntity userAddressEntity = userAddressService.getById(userAddressId);
		if(null==userAddressEntity) {
			String message="用户地址不存在";
			log.warn("order-commit-warn: userAddressId={} ,message={}",userAddressId,message);
			throw new BusinessException(message);
		}
		
		String userAddressJson = JSON.toJSONString(userAddressEntity);
	
		BigDecimal giftPrice = giftInfoEntity.getGiftPrice();
		BigDecimal realGiftPrice = giftInfoEntity.getRealGiftPrice();
		BigDecimal freightPrice =  BigDecimal.ZERO;
		buyerPayAmount =buyerPayAmount.add(freightPrice);
		sellIncome =sellIncome.add(freightPrice);
		
		OrderInfoEntity entity = new OrderInfoEntity();
		
		String orderNo = OrderUtil.getOrder(Consts.OrderType.PAY);
		Date date =new Date();
		
		
		entity.setCustomMade(customMade); 
		entity.setGiftAmount(giftAmount);
		entity.setGiftCode(giftCode); 
		entity.setOrderNo(orderNo);
		entity.setSpecification(specification);  
		entity.setUserId(userId);
		
//		默认超时30分钟有效
		Date expirationTime = DateKit.nowPlusMinutes(30);
		entity.setExpirationTime(expirationTime);
		entity.setFreightPrice(freightPrice);
		entity.setSellIncome(sellIncome);
		entity.setBuyerPayAmount(buyerPayAmount);
		entity.setGiftName(giftInfoEntity.getGiftName());
		entity.setGiftPrice(giftPrice);
//		entity.setMerchantNo(merchantNo);
//		entity.setPayChannel(payChannel);
		entity.setPayStatus(EnumConstants.PayStatusEnum.ORDER_PAY_PENDING.getCode()); 
		entity.setRealGiftPrice(realGiftPrice);
//		entity.setSettleStatus(settleStatus);
//		entity.setShipStatus(shipStatus);
		entity.setUserAddressJson(userAddressJson);
		
		entity.setStatus(0);
		entity.setUpdateTime(date);
		entity.setCreateTime(date);
		
		orderInfoMapper.insert(entity );
		
		log.info("order-commit-info: 订单提交  success",JSON.toJSONString(entity));
		 
//		设置返回值
		OrderCommitRespVO resp = new OrderCommitRespVO();
		UserAddressDto userAddressDto = new UserAddressDto();
		BeanKit.copyCglib(userAddressEntity, userAddressDto);
		userAddressDto.setUserAddressId(userAddressEntity.getId());
		
		resp.setBuyerPayAmount(buyerPayAmount.toString());
		resp.setOrderNo(orderNo);
		resp.setUserAddressDto(userAddressDto);
		
		log.info("order-commit-info: return resp={}",JSON.toJSONString(resp));
	 
		return resp;
	}

}