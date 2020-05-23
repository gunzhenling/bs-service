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
import com.bs.payment.modules.trade.vo.CarrierTracksVO;
import com.bs.payment.modules.trade.vo.CustomMadeVO;
import com.bs.payment.modules.trade.vo.OrderCarrierVO;
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
	public ZcPageResult<OrderInfoRespVO> bgGetOrderList(String orderNo, Integer madeType
			,Integer payStatus,Integer shipStatus,Long userId
			, Integer limit, Integer offset) {
		
		ZcPageResult<OrderInfoRespVO> page = new ZcPageResult<OrderInfoRespVO>();
		List<OrderInfoRespVO> bgOrderList = null;
		
		Long count = orderInfoMapper.getCount(orderNo, userId,madeType,payStatus,shipStatus);
		
		if(count==null) {
			
			bgOrderList = Lists.newArrayList();
			page.setData(bgOrderList);
			page.setTotal(0);
			
			return page;
		}
		
		bgOrderList = orderInfoMapper.getBgOrderList(orderNo, madeType,payStatus,shipStatus,userId,limit, offset);
		/*String rootPath = FileUtil.getRootPath();
		bgOrderList.forEach(order->{
			
			String pictureLogo = order.getPictureLogo();
			order.setPictureLogo(rootPath+pictureLogo);
		});*/
		
		page.setData(bgOrderList);
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
//	已发货
		isShip(entity,shipStatus);
//		已确认收货
		hadShip(entity,shipStatus);
//	退货中
		refundingShip(entity, shipStatus);
//	已退货
		refundedShip(entity, shipStatus);
		
		Date date = new Date();
		entity.setShipStatus(shipStatus);
		entity.setShipTime(date);
		entity.setUpdateTime(date);
		
		orderInfoMapper.updateById(entity);
	 
		log.info("order-updateShipStatus-info: orderNo={}, shipStatus={}  success",orderNo,shipStatus);
		 
		return Consts.SUCCESS;
	}
	
//退货中
	private void refundingShip(OrderInfoEntity entity,Integer shipStatus){
		
		if(EnumConstants.ShipStatusEnum.REFUNDING_SHIP.getCode().equals(shipStatus)){
			
			entity.setRefundIncome(entity.getBuyerPayAmount());
			entity.setRefundType(EnumConstants.OrderRefundStatusEnum.ORDER_PAY_REFUND_ALL.getCode());
			entity.setPayStatus(EnumConstants.PayStatusEnum.ORDER_REFUND_ING.getCode());
			
		}
		
	}
	
//已退货
	private void refundedShip(OrderInfoEntity entity,Integer shipStatus){
		
		if(EnumConstants.ShipStatusEnum.REFUNDED_SHIP.getCode().equals(shipStatus)){
			
			entity.setPayStatus(EnumConstants.PayStatusEnum.ORDER_REFUND_SUCCESS.getCode());
			
		}
		
	}
	
	
//已确认收货
			private void hadShip(OrderInfoEntity entity,Integer shipStatus){
				
				if(EnumConstants.ShipStatusEnum.HAD_SHIP.getCode().equals(shipStatus)){
			
				 String userAddressJson = entity.getUserAddressJson(); 
				 UserAddressEntity userAddressEntity = JSON.parseObject(userAddressJson,UserAddressEntity.class);
				 String province = userAddressEntity.getProvince();
				 String city = userAddressEntity.getCity();
				 String district = userAddressEntity.getDistrict();
				 String address = userAddressEntity.getAddress();
				 
				  String carrierTracksJson = entity.getCarrierTracksJson();
				OrderCarrierVO orderCarrierVO = JSON.parseObject(carrierTracksJson, OrderCarrierVO.class);
				List<CarrierTracksVO> carrierTracks = orderCarrierVO.getCarrierTracks();
				CarrierTracksVO carrierTracksVO = new CarrierTracksVO();
				carrierTracksVO.setAcceptStation("您的快件已签收。"+city);
				carrierTracksVO.setAcceptTime(DateKit.nowPlusMinutes(6));
				carrierTracks.add(carrierTracksVO);
				
				orderCarrierVO.setCarrierTracks(carrierTracks);
				
				carrierTracksJson = JSON.toJSONString(orderCarrierVO);
				entity.setCarrierTracksJson(carrierTracksJson);
		}
		
	}
		private void isShip(OrderInfoEntity entity,Integer shipStatus){
		
		if(EnumConstants.ShipStatusEnum.IS_SHIP.getCode().equals(shipStatus)){
			
			 String userAddressJson = entity.getUserAddressJson(); 
			 UserAddressEntity userAddressEntity = JSON.parseObject(userAddressJson,UserAddressEntity.class);
			 String province = userAddressEntity.getProvince();
			 String city = userAddressEntity.getCity();
			 String district = userAddressEntity.getDistrict();
			 String address = userAddressEntity.getAddress();
			
			String carrierTracksJson = entity.getCarrierTracksJson();
			OrderCarrierVO orderCarrierVO = JSON.parseObject(carrierTracksJson, OrderCarrierVO.class);
			List<CarrierTracksVO> carrierTracks = orderCarrierVO.getCarrierTracks();
			
			int madeType = entity.getMadeType();
			if(EnumConstants.MadeTypeEnum.MAN_MADE.getCode()==madeType){
				
				CarrierTracksVO carrier2 = new CarrierTracksVO();
				carrier2.setAcceptStation("【印刷中】上海市");
				carrier2.setAcceptTime(DateKit.nowPlusMinutes(2));
				carrierTracks.add(carrier2);
				
				CarrierTracksVO carrier3 = new CarrierTracksVO();
				carrier3.setAcceptStation("【已出库】上海市");
				carrier3.setAcceptTime(DateKit.nowPlusMinutes(3));
				carrierTracks.add(carrier3);
				
			}
			
			CarrierTracksVO tracks = new CarrierTracksVO();
			tracks.setAcceptStation("物流公司已揽件");
			tracks.setAcceptTime(DateKit.nowPlusMinutes(3));
			carrierTracks.add(tracks);
			
			CarrierTracksVO carrierTracksVO = new CarrierTracksVO();
			carrierTracksVO.setAcceptStation("快件到达【上海浦东集散中心2】上海市");
			carrierTracksVO.setAcceptTime(DateKit.nowPlusMinutes(4));
			carrierTracks.add(carrierTracksVO);
			
			CarrierTracksVO carrierTracksVO2 = new CarrierTracksVO();
			carrierTracksVO2.setAcceptStation("快件在【上海浦东集散中心2】已装车，准备发往【"+city+"中生中转场】上海市");
			carrierTracksVO2.setAcceptTime(DateKit.nowPlusMinutes(5));
			carrierTracks.add(carrierTracksVO2);
			
			CarrierTracksVO carrierTracksVO3 = new CarrierTracksVO();
			carrierTracksVO3.setAcceptStation("快件到达【"+city+"中生中转场】"+city);
			carrierTracksVO3.setAcceptTime(DateKit.nowPlusMinutes(6));
			carrierTracks.add(carrierTracksVO3);
			
			CarrierTracksVO carrierTracksVO4 = new CarrierTracksVO();
			carrierTracksVO4.setAcceptStation("快件到达【"+city+"物流园营业部】"+city);
			carrierTracksVO4.setAcceptTime(DateKit.nowPlusMinutes(7));
			carrierTracks.add(carrierTracksVO4);
			
			CarrierTracksVO carrierTracksVO5 = new CarrierTracksVO();
			carrierTracksVO5.setAcceptStation("正在派送途中，请您准备签收(派送人：小明，电话：17812345678)"+city);
			carrierTracksVO5.setAcceptTime(DateKit.nowPlusMinutes(8));
			carrierTracks.add(carrierTracksVO5);
			
			
			orderCarrierVO.setCarrierTracks(carrierTracks);
			
			carrierTracksJson = JSON.toJSONString(orderCarrierVO);
			entity.setCarrierTracksJson(carrierTracksJson);
			
		}
		
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
		
		OrderCarrierVO  orderCarrier = new OrderCarrierVO();
		List<CarrierTracksVO> carrierTracks = Lists.newArrayList() ;
		CarrierTracksVO tracks = new CarrierTracksVO();
		tracks.setAcceptStation("【订单已处理】上海市");
		tracks.setAcceptTime(date);
		carrierTracks.add(tracks);
		
		orderCarrier.setCarrierNo(OrderUtil.getOrder(Consts.OrderType.CARRIER));
		
		orderCarrier.setCarrierTracks(carrierTracks);
		orderCarrier.setCarrierType(0);
		orderCarrier.setCompanyName("韵达快递");
		orderCarrier.setCompanyNo("YD");
		orderCarrier.setCreateTime(date);
		orderCarrier.setOrderNo(orderNo);
		orderCarrier.setUpdateTime(date);
		orderCarrier.setCreateTime(date);
		
		entity.setCarrierTracksJson(JSON.toJSONString(orderCarrier));
		
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
		
		log.info("order-commit-info:  订单提交请求信息 req={}",JSON.toJSONString(req));
		
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
//		加上其他费用
		buyerPayAmount =buyerPayAmount.add(freightPrice) ;
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
		
		entity.setMadeType(giftInfoEntity.getMadeType());
		entity.setPictureLogo(req.getPictureLogo());
		
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


	@Override
	public String cancelPay(String orderNo) {
		 
		OrderInfoEntity entity = orderInfoMapper.selectOne(QueryBuilder.where("order_no", orderNo));
		if(entity==null){
			
			String message="订单不存在，无法取消";
			log.warn("order-cancelPay-warn: orderNo={} ,message={}",orderNo,message);
			throw new BusinessException(message);
		}
		
		entity.setPayStatus(EnumConstants.PayStatusEnum.ORDER_PAY_FAILURE.getCode());
		entity.setUpdateTime(DateKit.now());
		
		orderInfoMapper.updateById(entity);
		
		return Consts.SUCCESS;
	}

}
