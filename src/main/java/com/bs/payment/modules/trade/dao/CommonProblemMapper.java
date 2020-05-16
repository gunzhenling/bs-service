package com.bs.payment.modules.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.payment.modules.trade.entity.CommonProblemEntity;

/**
 *  留言
 * 
 * @author gunzhenling
 */
@Mapper
public interface CommonProblemMapper  extends BaseMapper<CommonProblemEntity> {
	
	Long getCount();
	
	List<CommonProblemEntity> getList(@Param("limit")Integer limit,@Param("offset") Integer offset);

}
