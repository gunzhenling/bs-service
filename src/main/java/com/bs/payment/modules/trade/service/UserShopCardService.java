package com.bs.payment.modules.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.modules.trade.dto.UserShopCardDto;
import com.bs.payment.modules.trade.entity.UserShopCardEntity;
import com.bs.payment.modules.trade.vo.ShopCommitReqVO;

import xyz.nesting.common.message.ZcPageResult;

public interface UserShopCardService extends IService<UserShopCardEntity> {
	
	/**
	 * 新增购物车
	 * @param dto
	 * @return
	 */
	String add(ShopCommitReqVO dto);
	
	/**
	 * 获取购物车列表
	 * @param dto
	 * @return
	 */
	ZcPageResult<UserShopCardDto> getList(Long userId,Integer limit,Integer offset);

}
