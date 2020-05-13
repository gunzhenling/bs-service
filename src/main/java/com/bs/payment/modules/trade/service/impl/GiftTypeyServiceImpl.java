package com.bs.payment.modules.trade.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.modules.trade.dao.GiftTypeMapper;
import com.bs.payment.modules.trade.entity.BsGiftTypeEntity;
import com.bs.payment.modules.trade.service.GiftTypeyService;
import com.bs.payment.util.QueryBuilder;
import com.google.common.collect.Lists;

@Service
public class GiftTypeyServiceImpl  extends ServiceImpl< GiftTypeMapper, BsGiftTypeEntity> implements GiftTypeyService {

	@Autowired
	private GiftTypeMapper giftTypeMapper;
	
	
	@Override
	public List<BsGiftTypeEntity> getGiftTypes() {
		

		List<BsGiftTypeEntity> selectList = giftTypeMapper.selectList(QueryBuilder.where("status",0));
		if(CollectionUtils.isEmpty(selectList)) {
			selectList = Lists.newArrayList();
		}
		
		return selectList;
	}
 
}
