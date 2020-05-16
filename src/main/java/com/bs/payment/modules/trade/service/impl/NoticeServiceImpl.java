package com.bs.payment.modules.trade.service.impl;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.common.constans.Consts;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.dao.NoticeMapper;
import com.bs.payment.modules.trade.dto.NoticeDto;
import com.bs.payment.modules.trade.entity.NoticeEntity;
import com.bs.payment.modules.trade.service.NoticeService;
import com.bs.payment.modules.trade.vo.NoticeReqVO;
import com.bs.payment.util.DateKit;

import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;


@Slf4j
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeEntity> implements NoticeService {
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Override
	public String add(NoticeReqVO req) {
		 
		
		NoticeEntity entity = new NoticeEntity();
		entity.setContent(req.getContent());
		entity.setTitle(req.getTitle());
		
//		entity.setStatus(status);
//		entity.setCreateTime(createTime);
//		entity.setUpdateTime(updateTime);
		
		noticeMapper.insert(entity );
		
		log.info("notice-add-info: insert success");
		
		return Consts.SUCCESS;
	}

	@Override
	public String update(NoticeReqVO req) {
		
		Long id = req.getId();
		if(id==null){
			
			String message="修改公告，主键id请求参数不能为空";
			log.warn("notice-update-warn: req={} ,message={}",JSON.toJSONString(req),message);
			throw new BusinessException(message);
			
		}
		
		NoticeEntity entity = noticeMapper.selectById(req.getId());
		if(entity==null){
			
			String message="公告不存在";
			log.warn("notice-update-warn: req={} ,message={}",JSON.toJSONString(req),message);
			throw new BusinessException(message);
			
		}
		
		entity.setContent(req.getContent());
		entity.setTitle(req.getTitle());
		 
		entity.setUpdateTime(DateKit.now());
		
		noticeMapper.updateById(entity );
		
		log.info("notice-update-info:  success");
		
		return Consts.SUCCESS;
	}

	@Override
	public String deleteById(Long id) {
		
		if(id==null){
			
			String message="删除公告，主键id请求参数不能为空";
			log.warn("notice-detele-warn:  message={}",message);
			throw new BusinessException(message);
			
		}
		 
		noticeMapper.deleteById(id);
		
		log.info("notice-delete-info:  success");
		
		return Consts.SUCCESS;
	}

	@Override
	public ZcPageResult<NoticeDto> getNoticeList(Integer limit, Integer offset) {
		 
		ZcPageResult<NoticeDto> page = new ZcPageResult<NoticeDto>();
		
		List<NoticeDto> noticeList  = null;
		Long count = noticeMapper.getCount();
		if(count==null){
			
			noticeList = Lists.newArrayList();
			page.setData(noticeList);
			page.setTotal(0);
		}
		
		noticeList = noticeMapper.getNoticeList(limit, offset);
		
		page.setData(noticeList);
		page.setTotal(count);
		
		return page;
	}

}
