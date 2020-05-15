package com.bs.payment.modules.trade.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.modules.trade.entity.BsGiftTypeEntity;

/**
 * 获取礼品类型接口
 * 
 * @author gunzhenling
 */
public interface GiftTypeyService extends IService<BsGiftTypeEntity> { 
	
	/**
	 * 获取礼品分类类型
	 * @return
	 */
	List<BsGiftTypeEntity> getGiftTypes();
	/**
	 * 礼品类型初始化接口
	 */
	void initGiftType();
	
}
