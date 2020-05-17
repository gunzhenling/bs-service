package com.bs.payment.modules.trade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.bs.payment.modules.trade.vo.BsGiftInfoRespVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;

/**
 * 礼品相关接口
 * 
 * @author gunzhenling
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
	
	@GetMapping("/search")
	@ApiOperation(value = "搜索关键词")
	public ZcResult<ZcPageResult<BsGiftInfoRespVO>> search(
			@RequestParam(value="search_query",required=true) String  searchQuery,
			@RequestParam(value="made_type",required=false) Integer madeType,
			@RequestParam(value="limit",required=false,defaultValue="10") Integer limit,
			@RequestParam(value="offset",required=false,defaultValue="0") Integer offset) throws Exception {
		
		log.info("GiftController-getList-info: request  searchQuery={},madeType={},limit={},offset={}"
				,searchQuery,madeType,limit,offset);
		
		ZcPageResult<BsGiftInfoRespVO> list = giftInfoService.search(searchQuery,madeType,limit, offset);
		 
		return ZcResult.ok(list);
	}
	
	@GetMapping("/get/list")
	@ApiOperation(value = "获取礼品列表")
	public ZcResult<ZcPageResult<BsGiftInfoRespVO>> getList(@RequestParam(value="type_code",required=false) Integer typeCode,
			@RequestParam(value="made_type",required=false) Integer madeType,
			@RequestParam(value="limit",required=false,defaultValue="5") Integer limit,@RequestParam(value="offset",required=false,defaultValue="0") Integer offset) throws Exception {
		
		log.info("GiftController-getList-info: request  type_code={},madeType={},limit={},offset={}",typeCode,madeType,limit,offset);
		
		ZcPageResult<BsGiftInfoRespVO> list = giftInfoService.getList(typeCode, madeType,limit, offset);
		 
		return ZcResult.ok(list);
	}
	
	@GetMapping("/get/detail")
	@ApiOperation(value = "获取礼品详情")
	public ZcResult<BsGiftInfoEntity> getDetail(@RequestParam(value="gift_code",required=true) Integer giftCode ) throws Exception {
		
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
	public ZcResult<?> add(@RequestBody BsGiftInfoReqVO giftInfoReqVO) throws Exception {
		
//		log.info("GiftController-add-info: request giftInfoReqVO={}",JSON.toJSONString(giftInfoReqVO));
		
		String result = giftInfoService.add(giftInfoReqVO);
		
		return ZcResult.ok(result);
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "更新礼品")
	public ZcResult<?> update(@RequestBody BsGiftInfoReqVO giftInfoReqVO) throws Exception {
		
		log.info("GiftController-add-info: request giftInfoReqVO={}",JSON.toJSONString(giftInfoReqVO));
		
		String result = giftInfoService.update(giftInfoReqVO);
		
		return ZcResult.ok(result);
	}
	
	@PostMapping("/delete/{giftCode}")
	@ApiOperation(value = "删除礼品")
	public ZcResult<?> delete(@PathVariable(value="giftCode",required=true) Integer giftCode) throws Exception {
		
		log.info("GiftController-delete-info: request giftCode={}",giftCode);
		
		String result = giftInfoService.delete(giftCode);
		 
		
		return ZcResult.ok(result);
	}
  
}
