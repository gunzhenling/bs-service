package com.bs.payment.modules.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.modules.trade.dto.NoticeDto;
import com.bs.payment.modules.trade.entity.NoticeEntity;
import com.bs.payment.modules.trade.vo.NoticeReqVO;

import xyz.nesting.common.message.ZcPageResult;

/**
 * 公告接口
 * @author DELL
 *
 */
public interface NoticeService extends IService<NoticeEntity> {
	
	
	/**
	 * 获取公告列表
	 * @param req
	 * @return
	 */
	
	ZcPageResult<NoticeDto> getNoticeList(Integer limit,Integer offset);
	
	/**
	 * 新增公告
	 * @param req
	 * @return
	 */
	
	String add(NoticeReqVO req);
	/**
	 * 修改公告
	 * @param req
	 * @return
	 */
	
	String update(NoticeReqVO req);
	/**
	 * 删除公告
	 * @param req
	 * @return
	 */
	
	String delete(Long id);

}
