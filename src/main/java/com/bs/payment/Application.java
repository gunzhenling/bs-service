package com.bs.payment;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableRetry
@EnableKafka
@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = { "com.bs.payment.modules.trade.dao" }, annotationClass = Mapper.class)
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
		 SpringApplication springApplication = new SpringApplication(Application .class);
		 springApplication.addListeners(new ApplicationStartup());
		 springApplication.run(args);
	}
	
	

}
