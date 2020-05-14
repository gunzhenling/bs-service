package com.bs.payment.modules.trade.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.entity.BankUserEntity;

/**
 *  用户账户
 * 
 * @author gunzhenling
 */
@Mapper
public interface BankUserMapper  extends BaseMapper<BankUserEntity>{

}
