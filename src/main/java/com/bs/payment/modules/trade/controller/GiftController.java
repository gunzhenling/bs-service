package com.bs.payment.modules.trade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bs.payment.common.ZcResult;
import com.bs.payment.modules.trade.entity.BsGiftInfoEntity;
import com.bs.payment.modules.trade.entity.BsGiftTypeEntity;
import com.bs.payment.modules.trade.service.GiftInfoService;
import com.bs.payment.modules.trade.service.GiftTypeyService;
import com.bs.payment.modules.trade.vo.BsGiftInfoReqVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 礼品相关接口
 * 
 * @author fanhang
 */
@Slf4j
@RestController
@RequestMapping("/bs/gift")
@Api(tags = "礼品相关接口")
public class GiftController {
 	
	@Autowired
	private GiftInfoService giftInfoService;
	
	@Autowired
	private GiftTypeyService giftTypeyService;
	
	@GetMapping("/get/list")
	@ApiOperation(value = "获取礼品列表")
	public ZcResult<?> getList(@RequestParam("type_code") Integer typeCode,@RequestParam("limit") Integer limit,@RequestParam("offset") Integer offset) throws Exception {
		
		log.info("GiftController-getList-info: request  type_code={},limit={},offset={}",typeCode,limit,offset);
		
		giftInfoService.getList(typeCode, limit, offset);
		 
		return ZcResult.ok();
	}
	
	@GetMapping("/get/detail")
	@ApiOperation(value = "获取礼品详情")
	public ZcResult<BsGiftInfoEntity> getDetail(@RequestParam("gift_code") Integer giftCode ) throws Exception {
		
		log.info("GiftController-getDetail-info: request  giftCode={} ",giftCode );
		
		BsGiftInfoEntity detail = giftInfoService.getDetail(giftCode);
		
		return ZcResult.ok(detail);
	}
	
	@GetMapping("/get/giftTypes")
	@ApiOperation(value = "获取礼品分类列表")
	public ZcResult<List<BsGiftTypeEntity>> getGiftTypes() throws Exception {
		
		log.info("GiftController-getGiftTypes-info: request ");
		
		List<BsGiftTypeEntity> giftTypes = giftTypeyService.getGiftTypes();
		
		return ZcResult.ok(giftTypes);
	}
	
	@PostMapping("/add")
	@ApiOperation(value = "新增礼品")
	public ZcResult<?> add(BsGiftInfoReqVO giftInfoReqVO) throws Exception {
		
		log.info("GiftController-add-info: request giftInfoReqVO={}",JSON.toJSONString(giftInfoReqVO));
		
		String result = giftInfoService.add(giftInfoReqVO);
		
		return ZcResult.ok(result);
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "更新礼品")
	public ZcResult<?> update(BsGiftInfoReqVO giftInfoReqVO) throws Exception {
		
		log.info("GiftController-add-info: request giftInfoReqVO={}",JSON.toJSONString(giftInfoReqVO));
		
		String result = giftInfoService.add(giftInfoReqVO);
		
		return ZcResult.ok(result);
	}
	
	@PostMapping("/delete/{giftCode}")
	@ApiOperation(value = "删除礼品")
	public ZcResult<?> delete(@PathVariable("giftCode") Integer giftCode) throws Exception {
		
		log.info("GiftController-delete-info: request giftCode={}",giftCode);
		
		String result = giftInfoService.delete(giftCode);
		
		return ZcResult.ok(result);
	}
  
}
