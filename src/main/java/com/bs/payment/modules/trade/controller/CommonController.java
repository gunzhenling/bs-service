package com.bs.payment.modules.trade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bs.payment.common.ZcResult;
import com.bs.payment.modules.trade.dto.UserAddressDto;
import com.bs.payment.modules.trade.entity.UserAddressEntity;
import com.bs.payment.modules.trade.service.UserAddressService;
import com.bs.payment.util.BeanKit;
import com.bs.payment.util.QueryBuilder;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

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
	private UserAddressService userAddressService;
	
	@GetMapping("/get/list")
	@ApiOperation(value = "test")
	public ZcResult<List<UserAddressDto>> getAddressList(@RequestParam(value="user_id",required=true) Integer userId) throws Exception {
		
		log.info("common-getAddressList-info: request  userId={} ",userId);
		
		List<UserAddressEntity> list = userAddressService.list(QueryBuilder.where("user_id", userId));
		List<UserAddressDto> dtoList = Lists.newArrayList();
		for(UserAddressEntity entity:list) {
			
			UserAddressDto dto = new UserAddressDto();
			BeanKit.copyCglib(entity, dto);
			dtoList.add(dto);
		}
		
		return ZcResult.ok(dtoList);
	}

}
