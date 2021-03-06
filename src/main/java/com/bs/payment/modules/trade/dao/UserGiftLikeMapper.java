package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.dto.UserGiftLikeDto;
import com.bs.payment.modules.trade.entity.UserGiftLikeEntity;

/**
 *  用户收藏
 * 
 * @author gunzhenling
 */
@Mapper
public interface UserGiftLikeMapper   extends BaseMapper<UserGiftLikeEntity>{
	
	Long getCount();
	
	List<UserGiftLikeDto> getUserGiftLikeList(@Param("limit")Integer limit,@Param("offset") Integer offset);
	
	int deleteGiftLikeById(@Param("id") Long id);

}
