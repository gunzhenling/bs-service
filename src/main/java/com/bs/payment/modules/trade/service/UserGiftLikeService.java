package com.bs.payment.modules.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.modules.trade.dto.UserGiftLikeDto;
import com.bs.payment.modules.trade.entity.UserGiftLikeEntity;
import com.bs.payment.modules.trade.vo.UserGiftLikeReqVO;

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
	ZcPageResult<UserGiftLikeDto> getUserGiftLikeList(Integer limit ,Integer offset);
	
	/**
	 * 用户新增礼品收藏
	 * @param dto
	 * @return
	 */
	String addUserGiftLike(UserGiftLikeReqVO dto);
	
	/**
	 * 用户取消收藏
	 * @param id
	 * @return
	 */
	String cancleUserGiftLike(Long id );
	/**
	 * 判断礼品是否用户已收藏
	 * @param id
	 * @return
	 */
	String validGiftLike(Integer giftCode );
	

}
