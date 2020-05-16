package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.dto.NoticeDto;
import com.bs.payment.modules.trade.entity.NoticeEntity;

/**
 *  公告
 * 
 * @author gunzhenling
 */
@Mapper
public interface NoticeMapper extends BaseMapper<NoticeEntity>{
	
		Long getCount();
		
		List<NoticeDto> getNoticeList(@Param("limit")Integer limit,@Param("offset") Integer offset);

	 
}
