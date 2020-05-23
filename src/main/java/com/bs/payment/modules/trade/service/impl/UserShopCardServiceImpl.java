package com.bs.payment.modules.trade.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.common.constans.Consts;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.dao.UserShopCardMapper;
import com.bs.payment.modules.trade.entity.BsGiftInfoEntity;
import com.bs.payment.modules.trade.entity.UserShopCardEntity;
import com.bs.payment.modules.trade.service.GiftInfoService;
import com.bs.payment.modules.trade.service.UserShopCardService;
import com.bs.payment.modules.trade.vo.CustomMadeVO;
import com.bs.payment.modules.trade.vo.ShopCommitReqVO;
import com.bs.payment.util.DateKit;
import com.bs.payment.util.QueryBuilder;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;

@Service
@Slf4j
public class UserShopCardServiceImpl extends ServiceImpl<UserShopCardMapper, UserShopCardEntity> implements UserShopCardService {
	
	@Autowired
	private UserShopCardMapper userShopCardMapper;
	@Autowired
	private GiftInfoService giftInfoService;
	
	
	@Override
	public String add(ShopCommitReqVO dto) {

		Integer giftCode = dto.getGiftCode();
		BsGiftInfoEntity bsGiftInfoEntity = giftInfoService.getOne(QueryBuilder.where("gift_code", giftCode));
		if(null==bsGiftInfoEntity) {
			String message="对应礼品不存在";
			log.warn("UserShopCard-add-warn:  BusinessException message={},giftCode={}",message,giftCode);
			throw new BusinessException(message);
		}
		
		UserShopCardEntity entity = new UserShopCardEntity();
		
		entity.setBuyerPayAmount(dto.getBuyerPayAmount());
		entity.setCustomMade(JSON.toJSONString(dto.getCustomMade()));
//		设置10天有效期
		entity.setExpirationTime(DateKit.nowPlusDays(10));
		entity.setGiftCode(dto.getGiftCode());
		entity.setGiftName(bsGiftInfoEntity.getGiftName());
		entity.setGiftPrice(bsGiftInfoEntity.getGiftPrice());
		entity.setRealGiftPrice(bsGiftInfoEntity.getRealGiftPrice());
		entity.setSellIncome(dto.getSellIncome());
		entity.setSpecification(JSON.toJSONString(dto.getSpecification()));
		entity.setUserId(dto.getUserId());
		entity.setPicture(dto.getPictureUrl());
		entity.setGiftAmount(dto.getGiftAmount());
		
		userShopCardMapper.insert(entity );
		
		log.info("UserShopCard-add-info:  新增购物车 success");

		return Consts.SUCCESS;
	}

	@Override
	public ZcPageResult<UserShopCardEntity> getList(Long userId, Integer limit, Integer offset) {
		 
		ZcPageResult<UserShopCardEntity> page = new ZcPageResult<UserShopCardEntity>();
		List<UserShopCardEntity> list = null;
		Long count = userShopCardMapper.getCount(userId);
		
		if(count==null) {
			
			list = Lists.newArrayList();
			page.setData(list);
			page.setTotal(0);
			
			return page;
		}
		
		list = userShopCardMapper.getList(userId, limit, offset);
		/*String rootPath = FileUtil.getRootPath();
		list.forEach(entity->{
			
			String picture = entity.getPicture();
			entity.setPicture(rootPath+picture);
		});*/
		
		
		page.setData(list);
		page.setTotal(count);
		
		log.info("UserShopCard-getList-info:  success");
		
		return page;
	}

	@Override
	public String cancleShops(Long id) {
		 
		userShopCardMapper.deleteShopCardById(id);
		

		log.info("UserShopCard-cancleShops-info:  success  id={}",id);
		 
		return Consts.SUCCESS;
	}

	@Override
	public String updateShopGiftAmount(Long id, Integer giftAmountReq) {
		 
		UserShopCardEntity userShopCardEntity = userShopCardMapper.selectById(id);
		if(null==userShopCardEntity){
			String message="对应购物车不存在";
			log.warn("UserShopCard-updateShopGiftAmount-warn:  BusinessException message={},id={}",message,id);
			throw new BusinessException(message);
		}
		if(0==giftAmountReq){
			
			cancleShops(id);
			
		}else{
			
			String customMadeJson = userShopCardEntity.getCustomMade();
			CustomMadeVO customMade = JSON.parseObject(customMadeJson, CustomMadeVO.class);
			String bFee = customMade.getBFee();
			String madeFee = customMade.getMadeFee();
			
			
			BigDecimal giftPrice = userShopCardEntity.getGiftPrice();
			BigDecimal realGiftPrice = userShopCardEntity.getRealGiftPrice();
			
			BigDecimal  sellIncome= giftPrice.multiply(new BigDecimal(giftAmountReq)).add(new BigDecimal(bFee)).add(new BigDecimal(madeFee));
			BigDecimal buyerPayAmount= realGiftPrice.multiply(new BigDecimal(giftAmountReq)).add(new BigDecimal(bFee)).add(new BigDecimal(madeFee));
			
			userShopCardEntity.setBuyerPayAmount(buyerPayAmount);
			userShopCardEntity.setSellIncome(sellIncome);
			userShopCardEntity.setGiftAmount(giftAmountReq);
			userShopCardEntity.setUpdateTime(DateKit.now());
			
			userShopCardMapper.updateById(userShopCardEntity);
			
		}
		 
		log.info("UserShopCard-updateShopGiftAmount-info:  success  id={},giftAmountReq={}",id,giftAmountReq);
		 
		
		return Consts.SUCCESS;
	}

}
