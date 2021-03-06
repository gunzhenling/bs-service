package com.bs.payment.modules.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.entity.BsGiftInfoEntity;
import com.bs.payment.modules.trade.vo.BsGiftInfoReqVO;
import com.bs.payment.modules.trade.vo.BsGiftInfoRespVO;

import xyz.nesting.common.message.ZcPageResult;

public interface GiftInfoService extends IService<BsGiftInfoEntity> {
	
	/**
	 * 获取礼品信息列表
	 * @param typeCode
	 * @param limit
	 * @param offset
	 * @return
	 */
	ZcPageResult<BsGiftInfoRespVO> getList(Integer typeCode,Integer madeType,Integer limit,Integer offset);
	/**
	 * 根据搜索词 获取礼品信息列表
	 * @param typeCode
	 * @param limit
	 * @param offset
	 * @return
	 */
	ZcPageResult<BsGiftInfoRespVO> search(String searchQuery,Integer madeType,Integer limit,Integer offset);
	
	/**
	 * 获取礼品具体信息
	 * @param giftCode
	 * @return
	 */
	BsGiftInfoEntity getDetail(Integer giftCode);
	
	/**
	 *  礼品新增
	 * @param giftInfoReqVO
	 * @return
	 */
	String add(BsGiftInfoReqVO giftInfoReqVO) throws BusinessException;
	/**
	 *  礼品更新
	 * @param giftInfoReqVO
	 * @return
	 */
	String update(BsGiftInfoReqVO giftInfoReqVO);
	/**
	 *  礼品删除
	 * @param giftCode
	 * @return
	 */
	String delete(Integer giftCode);

}
