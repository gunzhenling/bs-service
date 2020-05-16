package com.bs.payment.modules.trade.service.impl;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.payment.common.constans.Consts;
import com.bs.payment.common.exception.BusinessException;
import com.bs.payment.modules.trade.dao.UserMapper;
import com.bs.payment.modules.trade.dto.UpdatePasswordDto;
import com.bs.payment.modules.trade.dto.UserLoginDto;
import com.bs.payment.modules.trade.dto.UserRegisterDto;
import com.bs.payment.modules.trade.entity.UserEntity;
import com.bs.payment.modules.trade.service.BankUserService;
import com.bs.payment.modules.trade.service.UserService;
import com.bs.payment.modules.trade.vo.UserRespVO;
import com.bs.payment.util.MD5;
import com.bs.payment.util.QueryBuilder;

import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;


@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
 
	@Autowired
	private UserMapper  userMapper;
	@Autowired
	private BankUserService   bankUserService;

	@Transactional(rollbackFor=Exception.class)
	@Override
	public UserRespVO register(UserRegisterDto userDto) throws Exception {
		
		String icon = userDto.getIcon();
		String name = userDto.getName();
		String password = userDto.getPassword();
		String phone = userDto.getPhone();
		
		UserEntity userEntity = userMapper.selectOne(QueryBuilder.where("name", name));
		if(null!=userEntity) {
			
			String message="该昵称已被占用,请更换";
			log.warn("user-register-warn: name={} ,message={}",name,message);
			throw new BusinessException(message);
		}
		 
		UserEntity entity = new UserEntity();
		String passwordMd5Encode = MD5.MD5Encode(password);
		
		entity.setIcon(icon);
		entity.setName(name);
		entity.setPassword(passwordMd5Encode);
		entity.setPhone(phone);
		 
		userMapper.insert(entity);
		
		log.info("user-register-info: insert success  entity={}",JSON.toJSONString(entity));
		
		Long userId = entity.getId();
		bankUserService.generateBankUser(userId);
		
		
		UserRespVO resp = new UserRespVO();
		resp.setIcon(icon);
		resp.setName(name);
		resp.setPhone(phone);
		resp.setUserId(userId);
		 
		return resp;
	}

	@Override
	public UserRespVO login(UserLoginDto userLoginDto) {
		
		String name = userLoginDto.getName();
		String password = userLoginDto.getPassword();
		UserEntity userEntity = userMapper.selectOne(QueryBuilder.where("name", name));
		if(null==userEntity) {
			
			String message="用户不存在";
			log.warn("user-login-warn: userLoginDto={} ,message={}",JSON.toJSONString(userLoginDto),message);
			throw new BusinessException(message);
		}
		
		String passwordMd5Encode = MD5.MD5Encode(password);
		userEntity = userMapper.selectOne(QueryBuilder.where("name", name,"password",passwordMd5Encode));
		if(null==userEntity) {
			
			String message="用户密码不正确";
			log.warn("user-login-warn: userLoginDto={},passwordMd5Encode={} ,message={}"
					,JSON.toJSONString(userLoginDto),passwordMd5Encode,message);
			
			throw new BusinessException(message);
		}
		
		UserRespVO resp = new UserRespVO();
		resp.setIcon(userEntity.getIcon());
		resp.setName(userEntity.getName());
		resp.setPhone(userEntity.getPhone());
		resp.setUserId(userEntity.getId());
		
		log.info("user-register-info: login success  resp={}",JSON.toJSONString(resp));
		
		return resp;
	}

	@Override
	public String updatePassword(UpdatePasswordDto updatePasswordDto) {
		
		String name = updatePasswordDto.getName();
		String oldPassword = updatePasswordDto.getOldPassword();
		String password = updatePasswordDto.getPassword();//新密码
		String confirmPassword = updatePasswordDto.getConfirmPassword();//确认新密码
		
		String newMd5Encode = MD5.MD5Encode(password);
		String confirmNewMd5Encode = MD5.MD5Encode(confirmPassword);
		if(!newMd5Encode.equals(confirmNewMd5Encode)) {
			String message="新密码不一致，请重新输入";
			log.warn("user-updatePassword-warn: updatePasswordDto={},newMd5Encode={},confirmNewMd5Encode={} ,message={}"
					,JSON.toJSONString(updatePasswordDto),newMd5Encode,confirmNewMd5Encode,message);
			
			throw new BusinessException(message);
		}

		String passwordMd5Encode = MD5.MD5Encode(oldPassword);
		UserEntity userEntity = userMapper.selectOne(QueryBuilder.where("name", name,"password",passwordMd5Encode));
		if(null==userEntity) {
			
			String message="用户密码不正确";
			log.warn("user-updatePassword-warn: updatePasswordDto={},passwordMd5Encode={} ,message={}"
					,JSON.toJSONString(updatePasswordDto),passwordMd5Encode,message);
			
			throw new BusinessException(message);
		 }
		
		
		userEntity.setPassword(newMd5Encode);
		
		userMapper.updateById(userEntity);
		
		log.info("user-updatePassword-info:   success   newMd5Encode={}",newMd5Encode);
		
		return Consts.SUCCESS;
	}

	@Override
	public UserRespVO getUser(Long userId) {
		
		UserEntity userEntity = userMapper.selectById(userId);
		if(null==userEntity) {
			
			String message="用户不存在";
			log.warn("user-getUser-warn: userId={} ,message={}",userId,message);
			throw new BusinessException(message);
		}
		
		UserRespVO resp = new UserRespVO();
		resp.setIcon(userEntity.getIcon());
		resp.setName(userEntity.getName());
		resp.setPhone(userEntity.getPhone());
		resp.setUserId(userId);
		 
		log.info("user-getUser-info:  success ");
		
		
		return resp;
	}

	@Override
	public ZcPageResult<UserEntity> getUserList(Integer limit, Integer offset) {
		
		ZcPageResult<UserEntity> page = new ZcPageResult<UserEntity>();
		
		List<UserEntity> userList =null;
		Long count = userMapper.getCount();
		if(null==count){
			
			userList = Lists.newArrayList();
			page.setData(userList);
			page.setTotal(0);
			
			return page;
		}
		
		userList = userMapper.getUserList(limit, offset);
		
		page.setData(userList);
		page.setTotal(count);
		
		return page;
	}

}
