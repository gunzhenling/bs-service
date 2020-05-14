package com.bs.payment.modules.trade.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.common.constans.BankAvailableChangeEnum;
import com.bs.payment.common.constans.Consts;
import com.bs.payment.common.constans.Consts.OrderType;
import com.bs.payment.common.constans.EnumConstants;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.dao.BankUserMapper;
import com.bs.payment.modules.trade.dto.BankUserAvailableHistoryDto;
import com.bs.payment.modules.trade.dto.BankUserDto;
import com.bs.payment.modules.trade.entity.BankUserAvailableHistoryEntity;
import com.bs.payment.modules.trade.entity.BankUserEntity;
import com.bs.payment.modules.trade.service.BankUserAvailableHistoryService;
import com.bs.payment.modules.trade.service.BankUserService;
import com.bs.payment.modules.trade.vo.BankAvailableVO;
import com.bs.payment.modules.trade.vo.RechargeAvailableReqVO;
import com.bs.payment.util.BeanKit;
import com.bs.payment.util.DateKit;
import com.bs.payment.util.OrderUtil;
import com.bs.payment.util.QueryBuilder;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BankUserServiceImpl extends ServiceImpl<BankUserMapper, BankUserEntity> implements BankUserService {
	
	@Autowired
	private BankUserMapper bankUserMapper;
	@Autowired
	private BankUserAvailableHistoryService bankUserAvailableHistoryService;
	
	
	@Override
	public BankUserDto getBankUserDto(Long userId) {
		
		BankUserEntity entity = bankUserMapper.selectOne(QueryBuilder.where("user_id", userId));
		if(null==entity) {
			
//			用户注册初始化账户信息
			String message="用户不存在";
			log.warn("userbank-bank-warn: userId={} ,message={}",userId,message);
			throw new BusinessException(message);
		}
		
		BankUserDto dto = new BankUserDto();
		BeanKit.copyCglib(entity, dto);
		
		log.info("userbank-getBankUserDto-info:  success ,dto={}",JSON.toJSONString(dto));
		
		return dto;
	}


	@Override
	public void generateBankUser(Long userId) {
		
		if(null==userId) {
			
//			用户注册初始化账户信息
			String message="userId参数为空";
			log.warn("userbank-bank-warn: userId={} ,message={}",userId,message);
			throw new BusinessException(message);
		}
		 
		BankUserEntity entity = new BankUserEntity();
		
		entity.setAvailableMoney(BigDecimal.ZERO);
		entity.setCash(BigDecimal.ZERO);
		entity.setCurrencyIso(Consts.MonenyType.CNY);
		entity.setFrostMoney(BigDecimal.ZERO);
		entity.setIsLock(Consts.BankLockStatus.AVAIL);
		entity.setUserId(userId);
		
		bankUserMapper.insert(entity); 
		 
		log.info("userbank-generateBankUser-info:  success ,entity={}",JSON.toJSONString(entity));
		
		
	}


	@Override
	public BankAvailableVO getAvailableDetails(Long userId) {

		if(null==userId) {
			
			String message="userId参数为空";
			log.warn("userbank-getAvailableDetails-warn: userId={} ,message={}",userId,message);
			throw new BusinessException(message);
		}
		
		BankUserEntity bankUserEntity = bankUserMapper.selectById(userId);
		if(null==bankUserEntity) {
			
			String message="用户账户不存在";
			log.warn("userbank-getAvailableDetails-warn: userId={} ,message={}",userId,message);
			throw new BusinessException(message);
		}
		BankAvailableVO resp = new BankAvailableVO();
		
		BankUserDto bankUserDto = new BankUserDto();
		BeanKit.copyCglib(bankUserEntity, bankUserDto);
		
		resp.setBankUserDto(bankUserDto);
		
		List<BankUserAvailableHistoryDto> bankUserAvailableHistoryDtos = Lists.newArrayList();
		List<BankUserAvailableHistoryEntity> availableList = bankUserAvailableHistoryService.getBaseMapper().selectList(QueryBuilder.where("user_id", userId));
		if(!CollectionUtils.isEmpty(availableList)) {
			
			for(BankUserAvailableHistoryEntity entity:availableList) {
				
				BankUserAvailableHistoryDto dto = new BankUserAvailableHistoryDto();
				BeanKit.copyCglib(entity, dto);
				
				String changeTypeDesc = EnumConstants.BankChangeTypeEnum.getMessage(dto.getChangeType());
				dto.setChangeTypeDesc(changeTypeDesc);
				String isReduceDesc = EnumConstants.BankIsReduceEnum.getMessage(dto.getIsReduce());
				dto.setIsReduceDesc(isReduceDesc);
				
				bankUserAvailableHistoryDtos.add(dto);
				
			}
			
		} 
		
		resp.setBankUserAvailableHistoryDtos(bankUserAvailableHistoryDtos);
		
		log.info("userbank-getAvailableDetails-info:  success ,resp={}",JSON.toJSONString(resp));
		
		return resp;
	}


	@Override
	public String rechargeAvailable(RechargeAvailableReqVO reqVO) {
		 
		Long userId = reqVO.getUserId();
		
		if(null==userId) {
			
			String message="userId参数为空";
			log.warn("userbank-rechargeAvailable-warn: userId={} ,message={}",userId,message);
			throw new BusinessException(message);
		}
		BankUserEntity bankUserEntity = bankUserMapper.selectById(userId);
		if(null==bankUserEntity) {
			
			String message="用户账户不存在";
			log.warn("userbank-rechargeAvailable-warn: userId={} ,message={}",userId,message);
			throw new BusinessException(message);
		}
		
		BigDecimal moneny = reqVO.getMoneny();
		
		if(moneny.compareTo(BigDecimal.ZERO)!=1) {
			String message="充值金额为0";
			log.warn("userbank-rechargeAvailable-warn: userId={} ,message={}",userId,message);
			throw new BusinessException(message);
		}
		
//		更新账户
		BigDecimal availableMoney = bankUserEntity.getAvailableMoney();
		availableMoney = availableMoney.add(moneny);
		
		bankUserEntity.setAvailableMoney(availableMoney);
		bankUserEntity.setUpdateTime(DateKit.now());
		bankUserMapper.updateById(bankUserEntity);
		
		//		插入账户余额流水
		 
		
		BankUserAvailableHistoryEntity entity = new BankUserAvailableHistoryEntity();
		entity.setAvailableMoney(availableMoney);
		entity.setChangeMoney(moneny);
		entity.setChangeNo(OrderUtil.getOrder(OrderType.BANK_AVAIL));
		entity.setChangeType(BankAvailableChangeEnum.PAY.getType());
		entity.setDescription(BankAvailableChangeEnum.PAY.getDesc());
		entity.setIsReduce(BankAvailableChangeEnum.PAY.getIsReduce());
		entity.setUserId(userId);

		bankUserAvailableHistoryService.getBaseMapper().insert(entity);
		
		log.info("userbank-rechargeAvailable-info:  success ");
		
		
		return Consts.SUCCESS;
	}
 

}
