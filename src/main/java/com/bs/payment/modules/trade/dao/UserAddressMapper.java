package com.bs.payment.modules.trade.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.entity.UserAddressEntity;

/**
 *  用户地址
 * 
 * @author gunzhenling
 */
@Mapper
public interface UserAddressMapper  extends BaseMapper<UserAddressEntity>{
	
	int deleteUserAddressById(@Param("id") Long id);

}
