package com.bs.payment.modules.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.payment.modules.trade.dto.UserAddressDto;
import com.bs.payment.modules.trade.entity.UserAddressEntity;

public interface UserAddressService extends IService<UserAddressEntity> {
	
	/**
	 * 新增地址
	 * @param userAddressDto
	 * @return
	 */
	String add(UserAddressDto userAddressDto) throws Exception;
	/**
	 * 更新地址
	 * @param userAddressDto
	 * @return
	 */
	String update(UserAddressDto userAddressDto);
	/**
	 * 删除地址
	 * @param id
	 * @return
	 */
	String deleteUserAddressById(long id);

}
