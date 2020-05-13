package com.bs.payment.modules.trade.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.entity.BsGiftTypeEntity;

/**
 *  礼品类型
 * 
 * @author gunzhenling
 */
@Mapper
public interface GiftTypeMapper extends BaseMapper<BsGiftTypeEntity>  {

}
