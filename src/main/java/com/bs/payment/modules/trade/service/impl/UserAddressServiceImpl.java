package com.bs.payment.modules.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.common.constans.Consts;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.dao.UserAddressMapper;
import com.bs.payment.modules.trade.dto.UserAddressDto;
import com.bs.payment.modules.trade.entity.UserAddressEntity;
import com.bs.payment.modules.trade.service.UserAddressService;
import com.bs.payment.util.QueryBuilder;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddressEntity> implements UserAddressService {
	
	@Autowired
	private UserAddressMapper userAddressMapper;
	
	@Override
	public String add(UserAddressDto userAddressDto) {
		
		Integer isDefault = userAddressDto.getIsDefault();
		if(Consts.AddressDefaultType.YES==isDefault) {
			
			Integer userId = userAddressDto.getUserId();
			UserAddressEntity selectOne = userAddressMapper.selectOne(QueryBuilder.where("user_id", userId,"is_default",Consts.AddressDefaultType.YES));
			selectOne.setIsDefault(Consts.AddressDefaultType.NO);
			userAddressMapper.updateById(selectOne);
			
			log.info("address-add-info: 更新另一个默认地址  success  selectOne={}",JSON.toJSONString(selectOne));
			 
		}
		
		UserAddressEntity entity = new UserAddressEntity();
		
		entity.setAddress(userAddressDto.getAddress());
		entity.setCity(userAddressDto.getCity());
		entity.setCountry(userAddressDto.getCountry());
		entity.setDistrict(userAddressDto.getDistrict());
		entity.setIsDefault(userAddressDto.getIsDefault());
		entity.setName(userAddressDto.getName());
		entity.setPhone(userAddressDto.getPhone());
		entity.setProvince(userAddressDto.getProvince());
		entity.setUserId(userAddressDto.getUserId());
		
		userAddressMapper.insert(entity );
		
		log.info("address-add-info: insert  success  entity={}",JSON.toJSONString(entity));
		 
		return Consts.SUCCESS;
	}

	@Override
	public String update(UserAddressDto userAddressDto) {
		 
		Integer userAddressId = userAddressDto.getUserAddressId();
		if(null==userAddressId) {
			String message="原地址id请求参数必传";
			log.warn("order-commit-warn: userAddressDto={} ,message={}",JSON.toJSONString(userAddressDto),message);
			throw new BusinessException(message);
		}
		
		UserAddressEntity entity = userAddressMapper.selectById(userAddressId);
		if(null==entity) {
			String message="地址信息不存在";
			log.warn("order-commit-warn: userAddressId={} ,message={}",userAddressId,message);
			throw new BusinessException(message);
		}
		
		Integer isDefault = userAddressDto.getIsDefault();
		if(Consts.AddressDefaultType.YES==isDefault) {
			
			Integer userId = userAddressDto.getUserId();
			UserAddressEntity selectOne = userAddressMapper.selectOne(QueryBuilder.where("user_id", userId,"is_default",Consts.AddressDefaultType.YES));
			selectOne.setIsDefault(Consts.AddressDefaultType.NO);
			userAddressMapper.updateById(selectOne);
			
			log.info("address-add-info: 更新另一个默认地址  success  selectOne={}",JSON.toJSONString(selectOne));
			 
		}
		
		
		entity.setAddress(userAddressDto.getAddress());
		entity.setCity(userAddressDto.getCity());
		entity.setCountry(userAddressDto.getCountry());
		entity.setDistrict(userAddressDto.getDistrict());
		entity.setIsDefault(userAddressDto.getIsDefault());
		entity.setName(userAddressDto.getName());
		entity.setPhone(userAddressDto.getPhone());
		entity.setProvince(userAddressDto.getProvince());
		entity.setUserId(userAddressDto.getUserId());
		 
		userAddressMapper.updateById(entity);
		
		log.info("address-update-info: update  success  entity={}",JSON.toJSONString(entity));
		 
		return Consts.SUCCESS;
	}

}
