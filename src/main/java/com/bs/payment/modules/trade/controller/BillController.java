package com.bs.payment.modules.trade.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 账单相关接口
 * 
 * @author fanhang
 */
@Slf4j
@RestController
@RequestMapping("/trade/bill")
@Api(tags = "账单相关接口")
public class BillController {
	private static final String[] DATE_PATTERNS = {"yyyy-MM-dd", "yyyyMMdd"};
	
	 
  
}
