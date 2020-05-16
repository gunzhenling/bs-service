package com.bs.payment.modules.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.modules.trade.dto.UserGiftLikeDto;
import com.bs.payment.modules.trade.entity.UserGiftLikeEntity;

import xyz.nesting.common.message.ZcPageResult;

/**
 * 
 * @author DELL
 *
 */
public interface UserGiftLikeService extends IService<UserGiftLikeEntity> {
	
	/**
	 * 获取用户收藏lieb
	 * @param limit
	 * @param offset
	 * @return
	 */
	ZcPageResult<UserGiftLikeEntity> getUserGiftLikeList(Integer limit ,Integer offset);
	
	/**
	 * 用户新增礼品收藏
	 * @param dto
	 * @return
	 */
	String addUserGiftLike(UserGiftLikeDto dto);
	
	/**
	 * 用户取消收藏
	 * @param id
	 * @return
	 */
	String cancleUserGiftLike(Long id );
	

}
