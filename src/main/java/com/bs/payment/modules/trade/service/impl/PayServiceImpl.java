package com.bs.payment.modules.trade.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.bs.payment.common.constans.BankAvailableChangeEnum;
import com.bs.payment.common.constans.EnumConstants;
import com.bs.payment.common.constans.Consts.OrderType;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.dto.PayDto;
import com.bs.payment.modules.trade.entity.BankUserAvailableHistoryEntity;
import com.bs.payment.modules.trade.entity.BankUserEntity;
import com.bs.payment.modules.trade.service.BankUserAvailableHistoryService;
import com.bs.payment.modules.trade.service.BankUserService;
import com.bs.payment.modules.trade.service.PayService;
import com.bs.payment.util.DateKit;
import com.bs.payment.util.OrderUtil;
import com.bs.payment.util.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付接口
 * @author zhenling
 *
 */

@Slf4j
@Service
public class PayServiceImpl implements PayService {
	
	@Autowired
	private BankUserService bankUserService;
	@Autowired
	private BankUserAvailableHistoryService bankUserAvailableHistoryService;
	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean pay(PayDto payDto) {
		 
		log.info("payService-pay-info: request payDto={}",JSON.toJSONString(payDto));
		
		Long userId = payDto.getUserId();
		String orderNo = payDto.getOrderNo();
		String payChannel = payDto.getPayChannel();
		BigDecimal payMoney = payDto.getPayMoney();
		
		String code = EnumConstants.PayChannelEnum.getCode(payChannel);
		
		String yuE = EnumConstants.PayChannelEnum.YU_E.getCode();
		String wx = EnumConstants.PayChannelEnum.WX.getCode();
		String alipay = EnumConstants.PayChannelEnum.ALIPAY.getCode();
		
		if(yuE.equals(payChannel)) {
			
			yuEPay(userId,orderNo, payMoney);
			
		}else if(wx.equals(payChannel)) {
			
			String message="暂时不支持微信支付";
			log.warn("payService-pay-warn: orderNo={}, payChannel={},userId={} ,message={}",orderNo,payChannel,userId,message);
			throw new BusinessException(message);
			
		}  else if(alipay.equals(payChannel)) {
		
			String message="暂时不支持支付宝支付";
			log.warn("payService-pay-warn: orderNo={}, payChannel={} ,userId={} ,message={}",orderNo,payChannel,userId,message);
			throw new BusinessException(message);
		
		}else {
			
			String message="非法支付方式payChannel:"+payChannel;
			log.warn("payService-pay-warn: orderNo={}, payChannel={} ,userId={} ,message={}",orderNo,payChannel,userId,message);
			throw new BusinessException(message);
		}
		 
		log.info("payService-pay-info: pay success payDto={}",JSON.toJSONString(payDto));
		
		return true;
	}
	
	private  void yuEPay(Long userId,String orderNo,BigDecimal payMoney ) {
		
		BankUserEntity bankUserEntity = bankUserService.getOne(QueryBuilder.where("user_id", userId));
		if(null==bankUserEntity) {
			
			String message="用户账户不存在";
			log.warn("payService-yuEPay-warn: orderNo={}, payMoney={} ,userId={} ,message={}",orderNo,payMoney,userId,message);
			throw new BusinessException(message);
		
		}
		
		BigDecimal availableMoney = bankUserEntity.getAvailableMoney();
		if(availableMoney.compareTo(payMoney)==-1) {
			
			String message="用户账户余额不足,请充值!";
			log.warn("payService-yuEPay-warn: orderNo={}, payMoney={} ,userId={} ,message={}",orderNo,payMoney,userId,message);
			throw new BusinessException(message);
		}
		
		BigDecimal availableMoneyNew = availableMoney.subtract(payMoney);
		
		bankUserEntity.setAvailableMoney(availableMoneyNew);
		bankUserEntity.setUpdateTime(DateKit.now());
		
		bankUserService.updateById(bankUserEntity);
		
		log.info("payService-yuEPay-info:  update  用户账户 success ;orderNo={}, payMoney={} ,userId={}"
				+ ",availableMoney={},availableMoneyNew={}"
				,orderNo,payMoney,userId,availableMoney,availableMoneyNew);
		
		
		BankUserAvailableHistoryEntity vailableEntity = new BankUserAvailableHistoryEntity();
		
		vailableEntity.setAvailableMoney(availableMoneyNew);
		vailableEntity.setChangeMoney(payMoney);
		vailableEntity.setChangeNo(OrderUtil.getOrder(OrderType.BANK_AVAIL));
		vailableEntity.setChangeType(BankAvailableChangeEnum.PAY_ORDER.getType());
		vailableEntity.setDescription(BankAvailableChangeEnum.PAY_ORDER.getDesc());
		vailableEntity.setIsReduce(BankAvailableChangeEnum.PAY_ORDER.getIsReduce());
		vailableEntity.setStatus(0);
		vailableEntity.setUpdateTime(DateKit.now());
		vailableEntity.setCreateTime(DateKit.now());
		vailableEntity.setUserId(userId);
		
		bankUserAvailableHistoryService.getBaseMapper().insert(vailableEntity );
		
		log.info("payService-yuEPay-info:  update  用户账户流水 success ;orderNo={}, payMoney={} ,userId={},vailableEntity={}"
				,orderNo,payMoney,userId,JSON.toJSONString(vailableEntity));
		
		
	}

}
