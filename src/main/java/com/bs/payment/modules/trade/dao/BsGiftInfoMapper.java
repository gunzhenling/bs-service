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
public interface BsGiftInfoMapper extends BaseMapper<BsGiftInfoEntity>{

	 
	Long  getCount(@Param("typeCode")Integer typeCode,@Param("madeType") Integer madeType) ;
	
	Long  getSearchCount( @Param("searchQuery") String searchQuery,@Param("madeType") Integer madeType) ;
	
	List<BsGiftInfoRespVO>  getList(@Param("typeCode") Integer typeCode,@Param("madeType") Integer madeType,
			@Param("limit") Integer limit, @Param("offset")Integer offset) ;
	
	List<BsGiftInfoRespVO>  getSearchList(@Param("searchQuery") String searchQuery,
			@Param("madeType") Integer madeType,
			@Param("limit") Integer limit, @Param("offset")Integer offset) ;
 	 
	int  deteleByGiftCode(@Param("giftCode")Integer giftCode) ;
}
