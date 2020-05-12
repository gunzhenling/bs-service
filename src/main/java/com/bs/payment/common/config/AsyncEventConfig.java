package com.bs.payment.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步 evnet 配置
 * 
 * @author fanhang
 */
@Configuration
@ConfigurationProperties(prefix = "event")
public class AsyncEventConfig {
	
	private Integer corePollSize = 5;
	private Integer maxPollSize = 20;
	private Integer queueCapacity = 1000;
	private Integer keepAliveSeconds = 60;

	@Bean(name = "applicationEventMulticaster")
	public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		// 设置线程池参数
		taskExecutor.setCorePoolSize(corePollSize);
		taskExecutor.setMaxPoolSize(maxPollSize);
		taskExecutor.setQueueCapacity(queueCapacity);
		taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
		taskExecutor.setThreadNamePrefix("async-event-");
		taskExecutor.initialize();
		
		SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
		eventMulticaster.setTaskExecutor(taskExecutor);
		return eventMulticaster;
	}

}
