package com.bs.payment.modules.trade.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.entity.BankUserAvailableHistoryEntity;

/**
 *  用户账户余额
 * 
 * @author gunzhenling
 */
@Mapper
public interface BankUserAvailableHistoryMapper extends BaseMapper<BankUserAvailableHistoryEntity>{

}
