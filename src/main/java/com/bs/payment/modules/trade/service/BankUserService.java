package com.bs.payment.modules.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.modules.trade.dto.BankUserDto;
import com.bs.payment.modules.trade.entity.BankUserEntity;
import com.bs.payment.modules.trade.vo.BankAvailableVO;
import com.bs.payment.modules.trade.vo.RechargeAvailableReqVO;

/**
 * 用户账户
 * @author zhenling
 *
 */
public interface BankUserService extends IService<BankUserEntity> {
	
	/**
	 * 获取用户账户信息
	 * @param userId
	 * @return
	 */
	BankUserDto getBankUserDto(Long userId);
	/**
	 * 初始化用户账户信息
	 * @param userId
	 * @return
	 */
	void generateBankUser(Long userId);
	/**
	 * 初始化用户账户信息
	 * @param userId
	 * @return
	 */
	BankAvailableVO getAvailableDetails(Long userId);
	/**
	 * 充值
	 * @param userId
	 * @param moneny
	 * @return
	 */
	String rechargeAvailable(RechargeAvailableReqVO reqVO);

}
