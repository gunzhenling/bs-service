package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.entity.BsGiftInfoEntity;
import com.bs.payment.modules.trade.vo.BsGiftInfoRespVO;

/**
 *  礼品信息
 * 
 * @author gunzhenling
 */
@Mapper
//@Repository
public interface BsGiftInfoMapper extends BaseMapper<BsGiftInfoEntity>{

	 
	Long  getCount(@Param("typeCode")Integer typeCode) ;
	
	List<BsGiftInfoRespVO>  getList(@Param("typeCode") Integer typeCode,@Param("limit") Integer limit, @Param("offset")Integer offset) ;
 	 
	int  deteleByGiftCode(@Param("giftCode")Integer giftCode) ;
}
