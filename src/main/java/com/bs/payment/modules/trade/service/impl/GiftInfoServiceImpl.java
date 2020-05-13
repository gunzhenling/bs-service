package com.bs.payment.modules.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.common.Consts;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.dao.BsGiftInfoMapper;
import com.bs.payment.modules.trade.entity.BsGiftInfoEntity;
import com.bs.payment.modules.trade.service.GiftInfoService;
import com.bs.payment.modules.trade.vo.BsGiftInfoReqVO;
import com.bs.payment.modules.trade.vo.BsGiftInfoRespVO;
import com.bs.payment.util.FileUtil;
import com.bs.payment.util.QueryBuilder;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;


@Slf4j
@Service
public class GiftInfoServiceImpl extends ServiceImpl<BsGiftInfoMapper, BsGiftInfoEntity> implements GiftInfoService {

	@Autowired
	private BsGiftInfoMapper bsGiftInfoMapper;
	
	
	@Override
	public ZcPageResult<BsGiftInfoRespVO> getList(Integer typeCode, Integer limit, Integer offset) {
		
		 ZcPageResult<BsGiftInfoRespVO> page =  new ZcPageResult<BsGiftInfoRespVO>();
		 
		 List<BsGiftInfoRespVO> list = null;
		Long total = bsGiftInfoMapper.getCount(typeCode);
		page.setTotal(total);
		if(total==0) {
			
			list = Lists.newArrayList();
			page.setData(list);
			
			return page;
		}
		
		list = bsGiftInfoMapper.getList(typeCode, limit, offset);
		if(CollectionUtils.isEmpty(list)) {
			list = Lists.newArrayList();
		}
		
		page.setData(list);
		
		return page;
	}


	@Override
	public String add(BsGiftInfoReqVO giftInfoReqVO) {
		
//		 验证参数
		valid(giftInfoReqVO);
		
		Date date = new Date();
		MultipartFile pictureFile = giftInfoReqVO.getPictureFile();
		String uploadFileReturnBase64Str = FileUtil.uploadFileReturnBase64Str(pictureFile, Consts.FilelType.IMAGE);
//		新增
		BsGiftInfoEntity entity = new BsGiftInfoEntity();
		entity.setContent(giftInfoReqVO.getContent());
		entity.setCreateTime(date);
		entity.setCustomMade(giftInfoReqVO.getCustomMade());
		entity.setGiftCode(giftInfoReqVO.getGiftCode());
		entity.setGiftName(giftInfoReqVO.getGiftName());
		entity.setGiftPrice(giftInfoReqVO.getGiftPrice());
		entity.setLimitNum(giftInfoReqVO.getLimitNum());
		entity.setPicture(uploadFileReturnBase64Str);
		entity.setRealGiftPrice(giftInfoReqVO.getRealGiftPrice());
		entity.setSaleNum(giftInfoReqVO.getSaleNum());
		entity.setSpecification(giftInfoReqVO.getSpecification());
		entity.setStatus(0);
		entity.setTypeCode(giftInfoReqVO.getTypeCode());
		entity.setUpdateTime(date);
		 
		 
		bsGiftInfoMapper.insert(entity);
		
		log.info("giftInfo-add-info:  add success");
		
		return Consts.SUCCESS;
	}
	
	
	private void valid(BsGiftInfoReqVO giftInfoReqVO) {
		
		Integer giftCode = giftInfoReqVO.getGiftCode();
		if(giftCode==null) {
			 
			String message="giftCode请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}",message);
			throw new BusinessException(message);
		}
		String giftName = giftInfoReqVO.getGiftName();
		if(StringUtils.isBlank(giftName)) {
			
			String message="giftName请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}",message);
			throw new BusinessException(message);
		}
		 Integer typeCode = giftInfoReqVO.getTypeCode();
		if(typeCode==null) {
			
			String message="typeCode请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}",message);
			throw new BusinessException(message);
		}
		 BigDecimal giftPrice = giftInfoReqVO.getGiftPrice();
		if(giftPrice==null) {
			
			String message="giftPrice请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}",message);
			throw new BusinessException(message);
		}
		BigDecimal realGiftPrice = giftInfoReqVO.getRealGiftPrice();
		if(realGiftPrice==null) {
			
			String message="realGiftPrice请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}",message);
			throw new BusinessException(message);
		}
		
		 String specification = giftInfoReqVO.getSpecification();
		if(StringUtils.isBlank(specification)) {
			
			String message="specification请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}",message);
			throw new BusinessException(message);
		}
		
		String customMade = giftInfoReqVO.getCustomMade();
		if(StringUtils.isBlank(customMade)) {
			
			String message="customMade请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}",message);
			throw new BusinessException(message);
		}
		
	}


	@Override
	public String update(BsGiftInfoReqVO giftInfoReqVO) {
		
		
		Integer giftCode = giftInfoReqVO.getGiftCode();
		
		BsGiftInfoEntity entity = bsGiftInfoMapper.selectOne(QueryBuilder.where("gift_code", giftCode));
		if(entity==null) {
			 
			String message="对应记录礼品不存在giftCode="+giftCode;
			log.warn("giftInfo-update-warn:  BusinessException message={}",message);
			throw new BusinessException(message);
		}
		 
		Date date = new Date();
		MultipartFile pictureFile = giftInfoReqVO.getPictureFile();
		String uploadFileReturnBase64Str = FileUtil.uploadFileReturnBase64Str(pictureFile, Consts.FilelType.IMAGE);
//		新增
		entity.setContent(giftInfoReqVO.getContent());
		entity.setCustomMade(giftInfoReqVO.getCustomMade());
		entity.setGiftCode(giftInfoReqVO.getGiftCode());
		entity.setGiftName(giftInfoReqVO.getGiftName());
		entity.setGiftPrice(giftInfoReqVO.getGiftPrice());
		entity.setLimitNum(giftInfoReqVO.getLimitNum());
		
		if(StringUtils.isNotBlank(uploadFileReturnBase64Str)) {
			entity.setPicture(uploadFileReturnBase64Str);
		}
		
		entity.setRealGiftPrice(giftInfoReqVO.getRealGiftPrice());
		entity.setSaleNum(giftInfoReqVO.getSaleNum());
		entity.setSpecification(giftInfoReqVO.getSpecification());
		entity.setTypeCode(giftInfoReqVO.getTypeCode());
		entity.setUpdateTime(date);
		
		bsGiftInfoMapper.update(entity, QueryBuilder.where("gift_code", giftCode));
		
		log.info("giftInfo-update-info:  update success");
		
		return Consts.SUCCESS;
	}


	@Override
	public String delete(Integer giftCode) {
		
		if(giftCode==null) {
			
			String message="请求参数不能为空 giftCode="+giftCode;
			log.warn("giftInfo-delete-warn:  BusinessException message={}",message);
			throw new BusinessException(message);
		}
		
		bsGiftInfoMapper.delete(QueryBuilder.where("gift_code",giftCode));
		
		log.info("giftInfo-delete-info:  delete success gift_code={}",giftCode);
		
		return Consts.SUCCESS;
	}


	@Override
	public BsGiftInfoEntity getDetail(Integer giftCode) {
		 
		if(giftCode==null) {
			
			String message="请求参数不能为空 giftCode="+giftCode;
			log.warn("giftInfo-getDetail-warn:  BusinessException message={}",message);
			throw new BusinessException(message);
		}
	
		
		BsGiftInfoEntity entity = bsGiftInfoMapper.selectOne(QueryBuilder.where("gift_code",giftCode));
		Object picture = entity.getPicture();
		
		String baseDataTransferImage = FileUtil.baseDataTransferImage(picture);
		
		entity.setPicture(baseDataTransferImage);
		
		return entity;
	}
 

}
