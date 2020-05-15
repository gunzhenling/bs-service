package com.bs.payment.modules.trade.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
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
 * 地址接口
 * 
 * @author gunzhenling
 */
@Slf4j
@RestController
@RequestMapping("/bs/address")
@Api(tags = "地址接口")
public class AddressController {
	
	@Autowired
	private UserAddressService userAddressService;
	
	@GetMapping("/get/list")
	@ApiOperation(value = "获取收货地址列表")
	public ZcResult<List<UserAddressDto>> getAddressList(@RequestParam(value="user_id",required=true) Integer userId) throws Exception {
		
		log.info("address-getAddressList-info: request  userId={} ",userId);
		
		List<UserAddressEntity> list = userAddressService.list(QueryBuilder.where("user_id", userId));
		List<UserAddressDto> dtoList = Lists.newArrayList();
		for(UserAddressEntity entity:list) {
			
			UserAddressDto dto = new UserAddressDto();
			BeanKit.copyCglib(entity, dto);
			dto.setUserAddressId(entity.getId());
			dtoList.add(dto);
		}
		
		return ZcResult.ok(dtoList);
	}
	
	
	@PostMapping("/add")
	@ApiOperation(value = "新增收货地址")
	public ZcResult<String> add(@Valid@RequestBody UserAddressDto userAddressDto) throws Exception {
		
		log.info("address-add-info: request  userAddressDto={} ",JSON.toJSONString(userAddressDto));
		
		 String result = userAddressService.add(userAddressDto);
		 
		return ZcResult.ok(result);
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "更新收货地址")
	public ZcResult<String> update(@Valid@RequestBody UserAddressDto userAddressDto) throws Exception {
		
		log.info("address-update-info: request  userAddressDto={} ",JSON.toJSONString(userAddressDto));
		
		String result = userAddressService.update(userAddressDto);
		
		return ZcResult.ok(result);
	}

}
