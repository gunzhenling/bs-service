package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.entity.UserEntity;

/**
 *  用户
 * 
 * @author gunzhenling
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity>{
	
		Long getCount();
		
		List<UserEntity> getUserList(@Param("limit")Integer limit,@Param("offset") Integer offset);

	 
}
