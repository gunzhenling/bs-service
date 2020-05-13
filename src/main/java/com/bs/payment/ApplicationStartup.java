package com.bs.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.bs.payment.modules.trade.service.GiftTypeyService;

/**
 * spring boot 容器加载完成后执行
 * @author zhenling
 * @createAt 2020.05.13
 *
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext ac = event.getApplicationContext();
		GiftTypeyService giftTypeyService = ac.getBean(GiftTypeyService .class);
		giftTypeyService.initGiftType();
		logger.debug("服务基础信息初始化成功");
		
	}

}
