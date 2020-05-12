package com.bs.payment.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 对账单定时下载并解析入库
 * 
 * @author fanhang
 */
@Slf4j
@Component
public class BillDownloadTimer {

	 

	/**
	 * 每小时下载一次对账单(30日以内)
	 */
	@Scheduled(cron = "0 40 10 * * ?")
	// @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5 * 1000)
	public void billDownloadTask() {
		 
	}

	  
}
