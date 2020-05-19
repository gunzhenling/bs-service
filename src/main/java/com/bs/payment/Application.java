package com.bs.payment;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bs.payment.util.BsFileUtil;

@EnableRetry
@EnableKafka
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class Application {
	
	@Value("file.wxapp-config")
	private String configPath;

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
		 SpringApplication springApplication = new SpringApplication(Application .class);
		 springApplication.addListeners(new ApplicationStartup());
		 springApplication.run(args);
		 
		 BsFileUtil.replaConfigJsHostIp();
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("/tmp");//指定临时文件路径，这个路径可以随便写
//		factory.setLocation("d:/tmp");//指定临时文件路径，这个路径可以随便写
		return factory.createMultipartConfig();
	}

}
