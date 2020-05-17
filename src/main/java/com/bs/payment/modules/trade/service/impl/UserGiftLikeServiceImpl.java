package com.bs.payment.modules.trade.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.common.constans.Consts;
import com.bs.payment.modules.trade.dao.UserGiftLikeMapper;
import com.bs.payment.modules.trade.dto.UserGiftLikeDto;
import com.bs.payment.modules.trade.entity.UserGiftLikeEntity;
import com.bs.payment.modules.trade.service.UserGiftLikeService;
import com.bs.payment.modules.trade.vo.UserGiftLikeReqVO;
import com.bs.payment.util.QueryBuilder;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;

@Service
@Slf4j
public class UserGiftLikeServiceImpl extends ServiceImpl<UserGiftLikeMapper, UserGiftLikeEntity> implements UserGiftLikeService {

	@Autowired
	private UserGiftLikeMapper  userGiftLikeMapper; 
	
	@Override
	public ZcPageResult<UserGiftLikeDto> getUserGiftLikeList(Integer limit, Integer offset) {

		ZcPageResult<UserGiftLikeDto> page = new ZcPageResult<UserGiftLikeDto>();
		
		List<UserGiftLikeDto> userGiftLikeList  = null;
		
		Long count = userGiftLikeMapper.getCount();
		if(null==count){
			
			userGiftLikeList = Lists.newArrayList();
			page.setData(userGiftLikeList);
			page.setTotal(0);
			
			return page;
		}
		
	  userGiftLikeList = userGiftLikeMapper.getUserGiftLikeList(limit, offset);
		
		page.setData(userGiftLikeList);
		page.setTotal(count);
		
		return page;
		
	}

	@Override
	public String addUserGiftLike(UserGiftLikeReqVO req) {
		 
		UserGiftLikeEntity entity = new UserGiftLikeEntity();
		Integer giftCode = req.getGiftCode();
		Long userId = req.getUserId();
		
		UserGiftLikeEntity selectOne = userGiftLikeMapper.selectOne(QueryBuilder.where("gift_code", giftCode,"user_id",userId));
		if(selectOne!=null){
			
			return Consts.SUCCESS;
		}
		entity.setGiftCode(giftCode);
		entity.setUserId(userId);
		
		userGiftLikeMapper.insert(entity);
		
		log.info("giftLike-addUserGiftLike-info:   success");
		
		return Consts.SUCCESS;
	}

	@Override
	public String cancleUserGiftLike(Long id) {
		
		 
		userGiftLikeMapper.deleteGiftLikeById(id);
		
		log.info("giftLike-cancleUserGiftLike-info:   success");
		
		return Consts.SUCCESS;
	}

	@Override
	public Long validGiftLike(Integer giftCode) {

		UserGiftLikeEntity selectOne = userGiftLikeMapper.selectOne(QueryBuilder.where("gift_code", giftCode));
		if(null==selectOne){
			
			return 0l;
		}
		
		return selectOne.getId();
	}
 

}
