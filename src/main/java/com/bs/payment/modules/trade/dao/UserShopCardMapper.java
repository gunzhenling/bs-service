package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.dto.UserShopCardDto;
import com.bs.payment.modules.trade.entity.UserShopCardEntity;

/**
 *  购物车
 * 
 * @author gunzhenling
 */
@Mapper
public interface UserShopCardMapper extends BaseMapper<UserShopCardEntity>{
	
	Long getCount(@Param(value="userId")Long userId);
	
	List<UserShopCardEntity> getList(@Param(value="userId") Long userId,@Param(value="limit") Integer limit,@Param(value="offset") Integer offset);

	int deleteShopCardById(@Param(value="id")Long id);
}
