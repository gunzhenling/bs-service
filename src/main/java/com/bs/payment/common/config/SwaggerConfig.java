package com.bs.payment.common.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 配置
 * 仅开发及测试环境启用
 * 
 * @author fanhang
 */
@Configuration
@EnableSwagger2
@Profile({ "dev", "test", "szdev" })
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		ApiKey apiKey = new ApiKey("Authorization", "access_token", "header");
		
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[] { new AuthorizationScope("global", "accessEverything")};
        SecurityReference securityReference = SecurityReference.builder().reference("Authorization").scopes(authorizationScopes).build();
        SecurityContext securityContext = new SecurityContext(Collections.singletonList(securityReference), PathSelectors.any());
        
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.select().apis(RequestHandlerSelectors.basePackage("com.bs.payment.modules"))
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(Collections.singletonList(apiKey))
				.securityContexts(Collections.singletonList(securityContext));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("支付中心3.0接口文档").termsOfServiceUrl("").version("1.0")
				.build();
	}
}
