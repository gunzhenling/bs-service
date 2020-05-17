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
import com.bs.payment.modules.trade.dto.BankUserDto;
import com.bs.payment.modules.trade.dto.UpdatePasswordDto;
import com.bs.payment.modules.trade.dto.UserGiftLikeDto;
import com.bs.payment.modules.trade.dto.UserLoginDto;
import com.bs.payment.modules.trade.dto.UserRegisterDto;
import com.bs.payment.modules.trade.entity.UserEntity;
import com.bs.payment.modules.trade.service.BankUserService;
import com.bs.payment.modules.trade.service.UserGiftLikeService;
import com.bs.payment.modules.trade.service.UserService;
import com.bs.payment.modules.trade.vo.BankAvailableVO;
import com.bs.payment.modules.trade.vo.RechargeAvailableReqVO;
import com.bs.payment.modules.trade.vo.UserBankRespVO;
import com.bs.payment.modules.trade.vo.UserGiftLikeReqVO;
import com.bs.payment.modules.trade.vo.UserRespVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import xyz.nesting.common.message.ZcPageResult;

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
	
	
	@Autowired
	private UserGiftLikeService userGiftLikeService;
	
	
	@PostMapping("/register")
	@ApiOperation(value = "用户注册")
	public ZcResult<UserRespVO> register(@RequestBody UserRegisterDto userDto) throws Exception {
		
		log.info("user-register-info: request  userDto={} ",JSON.toJSONString(userDto));
		UserRespVO result = null;
		
		result = userService.register(userDto);
		/*try{
			 
		}catch(Exception e){
			e.printStackTrace();
			log.error("user-register-error:   err={}",e.getMessage());
			BusinessException.error(2, "服务异常");
		}*/
		 
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
	public ZcResult<String> rechargeAvailable(@Valid@RequestBody RechargeAvailableReqVO reqVO) throws Exception {
		
		log.info("user-rechargeAvailable-info: request  reqVO={} ",JSON.toJSONString(reqVO));
		
		String result = bankUserService.rechargeAvailable(reqVO);
		
		return ZcResult.ok(result);
	}
	
	@GetMapping("/get/list")
	@ApiOperation(value = "获取用户列表")
	public ZcResult<ZcPageResult<UserEntity>> getUserList(@RequestParam(value="limit",required=false,defaultValue="5")Integer limit,
			@RequestParam(value="offset",required=false,defaultValue="0")Integer offset) throws Exception {
		
		log.info("user-getInfo-info: request  limit={} ,offset={}",limit,offset);
		 
		ZcPageResult<UserEntity> userList = userService.getUserList(limit, offset);
		
		return ZcResult.ok(userList);
	}
	
	@PostMapping("/giftLike/add")
	@ApiOperation(value = "用户增加收藏")
	public ZcResult<String> addGiftLike(@Valid @RequestBody UserGiftLikeReqVO req) throws Exception {
		
		log.info("user-addGiftLike-info: request   req={}",JSON.toJSONString(req));
		 
		String result = userGiftLikeService.addUserGiftLike(req);
		
		return ZcResult.ok(result);
	}
	@PostMapping("/giftLike/cancle/{id}")
	@ApiOperation(value = "用户取消收藏")
	public ZcResult<String> cancleGiftLike(@PathVariable(value="id",required=true) Long id) throws Exception {
		
		log.info("user-cancleGiftLike-info: request  id={} ",id);
		 
		String result = userGiftLikeService.cancleUserGiftLike(id);
		
		return ZcResult.ok(result);
	}
	
	
	@GetMapping("/giftLike/getList")
	@ApiOperation(value = "用户获取收藏列表")
	public ZcResult<ZcPageResult<UserGiftLikeDto>> getListGiftLike(@RequestParam(value="limit",required=false,defaultValue="5")Integer limit,
			@RequestParam(value="offset",required=false,defaultValue="0")Integer offset) throws Exception {
		
		log.info("user-getListGiftLike-info: request  limit={} ,offset={}",limit,offset);
		
		 ZcPageResult<UserGiftLikeDto> userGiftLikeList = userGiftLikeService.getUserGiftLikeList(limit, offset);
		
		return ZcResult.ok(userGiftLikeList);
	}
	
	@PostMapping("/giftLike/valid/{gift_code}")
	@ApiOperation(value = "判断礼品是否用户已收藏")
	public ZcResult<Long> validGiftLike(@PathVariable(value="gift_code",required=true) Integer giftCode) throws Exception {
		
		log.info("user-validGiftLike-info: request   giftCode={}",giftCode);
		 
		Long result = userGiftLikeService.validGiftLike(giftCode);
		
		return ZcResult.ok(result);
	}
	
}
