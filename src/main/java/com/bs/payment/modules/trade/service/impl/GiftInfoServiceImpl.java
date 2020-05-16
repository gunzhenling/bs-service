package com.bs.payment.modules.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.common.constans.Consts;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.dao.BsGiftInfoMapper;
import com.bs.payment.modules.trade.entity.BsGiftInfoEntity;
import com.bs.payment.modules.trade.service.GiftInfoService;
import com.bs.payment.modules.trade.vo.BsGiftInfoReqVO;
import com.bs.payment.modules.trade.vo.BsGiftInfoRespVO;
import com.bs.payment.modules.trade.vo.CustomMadeVO;
import com.bs.payment.modules.trade.vo.SpecificationVO;
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
	public ZcPageResult<BsGiftInfoRespVO> getList(Integer typeCode, Integer madeType, Integer limit, Integer offset) {

		ZcPageResult<BsGiftInfoRespVO> page = new ZcPageResult<BsGiftInfoRespVO>();

		List<BsGiftInfoRespVO> list = null;
		Long total = bsGiftInfoMapper.getCount(typeCode, madeType);
		page.setTotal(total);
		if (total == 0) {

			list = Lists.newArrayList();
			page.setData(list);

			return page;
		}

		list = bsGiftInfoMapper.getList(typeCode, madeType, limit, offset);
		if (CollectionUtils.isEmpty(list)) {
			list = Lists.newArrayList();
		} else {

			String rootPath = FileUtil.getRootPath();
			list.forEach(dto -> {

				String picture = dto.getPicture();
				dto.setPicture(rootPath + picture);

			});

		}

		page.setData(list);

		return page;
	}

	@Override
	public String add(BsGiftInfoReqVO giftInfoReqVO) throws BusinessException {

		// 验证参数
		valid(giftInfoReqVO);

		Date date = new Date();
		// MultipartFile pictureFile = giftInfoReqVO.getPictureFile();
		// String uploadFileReturnBase64Str =
		// FileUtil.uploadFileReturnBase64Str(pictureFile,
		// Consts.FilelType.IMAGE);
		// 新增
		BsGiftInfoEntity entity = new BsGiftInfoEntity();
		entity.setContent(giftInfoReqVO.getContent());
		entity.setCreateTime(date);
		entity.setCustomMade(JSON.toJSONString(giftInfoReqVO.getCustomMade()));
		entity.setGiftCode(giftInfoReqVO.getGiftCode());
		entity.setGiftName(giftInfoReqVO.getGiftName());
		entity.setGiftPrice(giftInfoReqVO.getGiftPrice());
		entity.setLimitNum(giftInfoReqVO.getLimitNum());
		entity.setPicture(giftInfoReqVO.getPictureUrl());
		entity.setRealGiftPrice(giftInfoReqVO.getRealGiftPrice());
		entity.setSaleNum(giftInfoReqVO.getSaleNum());
		entity.setSpecification(JSON.toJSONString(giftInfoReqVO.getSpecification()));
		entity.setStatus(0);
		entity.setTypeCode(giftInfoReqVO.getTypeCode());
		entity.setUpdateTime(date);
		entity.setMadeType(giftInfoReqVO.getMadeType());

		bsGiftInfoMapper.insert(entity);

		log.info("giftInfo-add-info:  add success");

		return Consts.SUCCESS;
	}

	private void valid(BsGiftInfoReqVO giftInfoReqVO) throws BusinessException {

		Integer giftCode = giftInfoReqVO.getGiftCode();
		if (giftCode == null) {

			String message = "giftCode请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}

		BsGiftInfoEntity entity = bsGiftInfoMapper.selectOne(QueryBuilder.where("gift_code", giftCode));
		if (entity != null) {

			String message = "对应记录礼品已存在,礼品编码不可重复giftCode=" + giftCode;
			log.warn("giftInfo-update-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}

		String giftName = giftInfoReqVO.getGiftName();
		if (StringUtils.isBlank(giftName)) {

			String message = "giftName请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}
		Integer typeCode = giftInfoReqVO.getTypeCode();
		if (typeCode == null) {

			String message = "typeCode请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}
		BigDecimal giftPrice = giftInfoReqVO.getGiftPrice();
		if (giftPrice == null) {

			String message = "giftPrice请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}
		BigDecimal realGiftPrice = giftInfoReqVO.getRealGiftPrice();
		if (realGiftPrice == null) {

			String message = "realGiftPrice请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}

		List<SpecificationVO> specification = giftInfoReqVO.getSpecification();
		if (CollectionUtils.isEmpty(specification)) {

			String message = "specification请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}

		List<CustomMadeVO> customMade = giftInfoReqVO.getCustomMade();
		if (CollectionUtils.isEmpty(customMade)) {

			String message = "customMade请求参数为空";
			log.warn("giftInfo-update-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}

	}

	@Override
	public String update(BsGiftInfoReqVO giftInfoReqVO) {

		Integer giftCode = giftInfoReqVO.getGiftCode();

		BsGiftInfoEntity entity = bsGiftInfoMapper.selectOne(QueryBuilder.where("gift_code", giftCode));
		if (entity == null) {

			String message = "对应记录礼品不存在giftCode=" + giftCode;
			log.warn("giftInfo-update-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}

		Date date = new Date();
		// MultipartFile pictureFile = giftInfoReqVO.getPictureFile();
		// String uploadFileReturnBase64Str =
		// FileUtil.uploadFileReturnBase64Str(pictureFile,
		// Consts.FilelType.IMAGE);
		// 新增
		entity.setContent(giftInfoReqVO.getContent());
		entity.setCustomMade(JSON.toJSONString(giftInfoReqVO.getCustomMade()));
		entity.setGiftCode(giftInfoReqVO.getGiftCode());
		entity.setGiftName(giftInfoReqVO.getGiftName());
		entity.setGiftPrice(giftInfoReqVO.getGiftPrice());
		entity.setLimitNum(giftInfoReqVO.getLimitNum());

		if (StringUtils.isNotBlank(giftInfoReqVO.getPictureUrl())) {
			entity.setPicture(giftInfoReqVO.getPictureUrl());
		}

		entity.setRealGiftPrice(giftInfoReqVO.getRealGiftPrice());
		entity.setSaleNum(giftInfoReqVO.getSaleNum());
		entity.setSpecification(JSON.toJSONString(giftInfoReqVO.getSpecification()));
		entity.setTypeCode(giftInfoReqVO.getTypeCode());
		entity.setUpdateTime(date);

		bsGiftInfoMapper.update(entity, QueryBuilder.where("gift_code", giftCode));

		log.info("giftInfo-update-info:  update success");

		return Consts.SUCCESS;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String delete(Integer giftCode) {

		BsGiftInfoEntity selectOne = bsGiftInfoMapper.selectOne(QueryBuilder.where("gift_code", giftCode));
		if (giftCode == null || selectOne == null) {

			String message = "请求参数不能为空 giftCode=" + giftCode;
			log.warn("giftInfo-delete-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}
		// 该操作是软删，status置为1
		// bsGiftInfoMapper.delete(QueryBuilder.where("gift_code",giftCode));
		bsGiftInfoMapper.deteleByGiftCode(giftCode);

		// 删除本地图片
		String fileUrlPrefix = selectOne.getPicture();
		FileUtil.deleteFileImage(fileUrlPrefix);

		log.info("giftInfo-delete-info:  delete success gift_code={}", giftCode);

		return Consts.SUCCESS;
	}

	@Override
	public BsGiftInfoEntity getDetail(Integer giftCode) {

		if (giftCode == null) {

			String message = "请求参数不能为空 giftCode=" + giftCode;
			log.warn("giftInfo-getDetail-warn:  BusinessException message={}", message);
			throw new BusinessException(message);
		}

		BsGiftInfoEntity entity = bsGiftInfoMapper.selectOne(QueryBuilder.where("gift_code", giftCode));
		if (entity == null) {
			return null;
		}

		// 数据库存储图片的base64二进制
		// Object picture = entity.getPicture();
		// String baseDataTransferImage =
		// FileUtil.baseDataTransferImage(picture);

		// 数据库中存储图片的相对地址
		String picture = (String) entity.getPicture();
		String rootPath = FileUtil.getRootPath();
		picture = rootPath + picture;
		entity.setPicture(picture);

		return entity;
	}

}
