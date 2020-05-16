package com.bs.payment;

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
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
		 SpringApplication springApplication = new SpringApplication(Application .class);
		 springApplication.addListeners(new ApplicationStartup());
		 springApplication.run(args);
	}
	
	

}
