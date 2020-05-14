package com.bs.payment.modules.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bs.payment.common.ZcResult;
import com.bs.payment.modules.trade.dto.BankUserDto;
import com.bs.payment.modules.trade.dto.UpdatePasswordDto;
import com.bs.payment.modules.trade.dto.UserLoginDto;
import com.bs.payment.modules.trade.dto.UserRegisterDto;
import com.bs.payment.modules.trade.service.BankUserService;
import com.bs.payment.modules.trade.service.UserService;
import com.bs.payment.modules.trade.vo.BankAvailableVO;
import com.bs.payment.modules.trade.vo.RechargeAvailableReqVO;
import com.bs.payment.modules.trade.vo.UserBankRespVO;
import com.bs.payment.modules.trade.vo.UserRespVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户相关接口
 * 
 * @author gunzhenling
 */
@Slf4j
@RestController
@RequestMapping("/bs/user")
@Api(tags = "用户相关接口")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BankUserService bankUserService;
	
	
	@PostMapping("/register")
	@ApiOperation(value = "用户注册")
	public ZcResult<UserRespVO> register(@RequestBody UserRegisterDto userDto) throws Exception {
		
		log.info("user-register-info: request  userDto={} ",JSON.toJSONString(userDto));
		
		UserRespVO result = userService.register(userDto);
		 
		return ZcResult.ok(result);
	}
	
	@PostMapping("/login")
	@ApiOperation(value = "用户登录")
	public ZcResult<UserRespVO> login(@RequestBody UserLoginDto userLoginDto) throws Exception {
		
		log.info("user-login-info: request  userLoginDto={} ",JSON.toJSONString(userLoginDto));
		
		UserRespVO result = userService.login(userLoginDto);
		
		return ZcResult.ok(result);
	}
	
	
	@PostMapping("/update/password")
	@ApiOperation(value = "用户更改密码")
	public ZcResult<String> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) throws Exception {
		
		log.info("user-updatePassword-info: request  updatePasswordDto={} ",JSON.toJSONString(updatePasswordDto));
		
		String result = userService.updatePassword(updatePasswordDto);
		
		return ZcResult.ok(result);
	}
	
	
	@GetMapping("/get/info")
	@ApiOperation(value = "个人中心：获取用户信息+账户余额")
	public ZcResult<UserBankRespVO> getInfo(@RequestParam(value="user_id",required=true) Long userId) throws Exception {
		
		log.info("user-getInfo-info: request  userId={} ",userId);
		
		UserBankRespVO resp = new UserBankRespVO();
		UserRespVO user = userService.getUser(userId);
		
		BankUserDto bankUserDto = bankUserService.getBankUserDto(userId);
		
		resp.setBankUserDto(bankUserDto);
		resp.setUserRespVO(user);
		
		return ZcResult.ok(resp);
	}
	
	
	@GetMapping("/get/available/details")
	@ApiOperation(value = "获取我的余额+余额明细")
	public ZcResult<BankAvailableVO> getAvailableDetails(@RequestParam(value="user_id",required=true) Long userId) throws Exception {
		
		log.info("user-getInfo-info: request  userId={} ",userId);
		 
		 BankAvailableVO availableDetails = bankUserService.getAvailableDetails(userId);
		
		return ZcResult.ok(availableDetails);
	}
	
	@PostMapping("/recharge/available")
	@ApiOperation(value = "余额充值")
	public ZcResult<String> rechargeAvailable(@RequestBody RechargeAvailableReqVO reqVO) throws Exception {
		
		log.info("user-rechargeAvailable-info: request  reqVO={} ",JSON.toJSONString(reqVO));
		
		String result = bankUserService.rechargeAvailable(reqVO);
		
		return ZcResult.ok(result);
	}
	
	
	
}
