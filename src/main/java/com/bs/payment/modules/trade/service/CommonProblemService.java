package com.bs.payment.modules.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.modules.trade.entity.CommonProblemEntity;

import xyz.nesting.common.message.ZcPageResult;

public interface CommonProblemService extends IService<CommonProblemEntity> {
	
	ZcPageResult<CommonProblemEntity> getProblemList(Integer limit,Integer offset);

}
