package com.bs.payment.modules.trade.controller;

import javax.validation.Valid;

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
import com.bs.payment.modules.trade.dto.NoticeDto;
import com.bs.payment.modules.trade.entity.CommonProblemEntity;
import com.bs.payment.modules.trade.service.CommonProblemService;
import com.bs.payment.modules.trade.service.NoticeService;
import com.bs.payment.modules.trade.vo.NoticeReqVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;

/**
 * 公共接口
 * 
 * @author gunzhenling
 */
@Slf4j
@RestController
@RequestMapping("/bs/common")
@Api(tags = "公共接口")
public class CommonController {
	
	@Autowired
	private NoticeService  	noticeService;
	
	@Autowired
	private CommonProblemService  	commonProblemService;
	
	
	@GetMapping("/operation/guide/get")
	@ApiOperation(value = "获取操作指南")
	public ZcResult<String> getOperationGuide() throws Exception {
		
		log.info("common-getOperationGuide-info: request  ");
		
		String result="";
		
		return ZcResult.ok(result);
	}
	@GetMapping("/problem/get/list")
	@ApiOperation(value = "获取留言列表")
	public ZcResult<ZcPageResult<CommonProblemEntity> > getProblemList(@RequestParam(value="limit",required=false,defaultValue="5")Integer limit,
			@RequestParam(value="offset",required=false,defaultValue="0")Integer offset) throws Exception {
		
		log.info("common-getProblemList-info: request  limit={} ,offset={}",limit,offset);
		
		ZcPageResult<CommonProblemEntity> problemList = commonProblemService.getProblemList(limit, offset);
		
		return ZcResult.ok(problemList);
	}
	@GetMapping("/notice/get/list")
	@ApiOperation(value = "获取公告列表")
	public ZcResult<ZcPageResult<NoticeDto> > getNoticeList(@RequestParam(value="limit",required=false,defaultValue="5")Integer limit,
			@RequestParam(value="offset",required=false,defaultValue="0")Integer offset) throws Exception {
		
		log.info("common-getNoticeList-info: request  limit={} ,offset={}",limit,offset);
		
		ZcPageResult<NoticeDto> noticeList = noticeService.getNoticeList(limit, offset);
		
		return ZcResult.ok(noticeList);
	}
	
	@PostMapping("/notice/add")
	@ApiOperation(value = "新增公告")
	public ZcResult<String> addNotice(@Valid@RequestBody NoticeReqVO req) throws Exception {
		
		log.info("common-addNotice-info: request  req={} ",JSON.toJSONString(req));
		
		String result = noticeService.add(req);
		
		return ZcResult.ok(result);
	}
	@PostMapping("/notice/update")
	@ApiOperation(value = "修改公告")
	public ZcResult<String> updateNotice(@Valid@RequestBody NoticeReqVO req) throws Exception {
		
		log.info("common-updateNotice-info: request  req={} ",JSON.toJSONString(req));
		
		String result = noticeService.update(req);
		
		return ZcResult.ok(result);
	}
	@PostMapping("/notice/delete/{id}")
	@ApiOperation(value = "删除公告")
	public ZcResult<String> deleteNotice(@PathVariable(value="id",required=true) Long id) throws Exception {
		
		log.info("common-deleteNotice-info: request  id={} ",id);
		
		String result = noticeService.delete(id);
		
		return ZcResult.ok(result);
	}

}
