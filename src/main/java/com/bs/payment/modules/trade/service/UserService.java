package com.bs.payment.modules.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.modules.trade.dto.UpdatePasswordDto;
import com.bs.payment.modules.trade.dto.UserLoginDto;
import com.bs.payment.modules.trade.dto.UserRegisterDto;
import com.bs.payment.modules.trade.entity.UserEntity;
import com.bs.payment.modules.trade.vo.UserRespVO;

public interface UserService extends IService<UserEntity> {
	
	/**
	 * 用户注册
	 * @param userDto
	 * @return
	 */
	UserRespVO register(UserRegisterDto userDto)throws  Exception;
	
	/**
	 *  用户登录
	 * @param userLoginDto
	 * @return
	 */
	UserRespVO login(UserLoginDto userLoginDto);
	/**
	 *  更新密码
	 * @param userLoginDto
	 * @return
	 */
	String updatePassword(UpdatePasswordDto updatePasswordDto);
	 

	/**
	 * 获取用户信息
	 * @param userDto
	 * @return
	 */
	UserRespVO getUser(Long userId);
}
