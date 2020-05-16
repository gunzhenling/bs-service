package com.bs.payment.modules.trade.service.impl;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.modules.trade.dao.CommonProblemMapper;
import com.bs.payment.modules.trade.entity.CommonProblemEntity;
import com.bs.payment.modules.trade.service.CommonProblemService;

import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;

@Service
@Slf4j
public class CommonProblemServiceImpl extends ServiceImpl<CommonProblemMapper,CommonProblemEntity> implements CommonProblemService {

	@Autowired
	private CommonProblemMapper commonProblemMapper;
	
	@Override
	public ZcPageResult<CommonProblemEntity> getProblemList(Integer limit, Integer offset) {
			
		ZcPageResult<CommonProblemEntity> page = new ZcPageResult<CommonProblemEntity>();
		
		List<CommonProblemEntity> list = null;
		Long count = commonProblemMapper.getCount();
		if(null==count){
			
			list = Lists.newArrayList();
			page.setData(list);
			page.setTotal(count);
			
			return page;
		}
		
		list = commonProblemMapper.getList(limit, offset);
		
		page.setData(list);
		page.setTotal(count);
		
		return page;
	
	}

}
