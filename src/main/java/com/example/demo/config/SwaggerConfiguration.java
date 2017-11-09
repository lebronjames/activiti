package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile({"default", "dev", "test", "pre"})
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	        .title("Activiti项目")
	        .description("Activiti项目")
	        .termsOfServiceUrl("")
	        .version("1.0.0")
	        .contact(new Contact("", "", ""))
	        .build();
	  }

	  @Bean
	  public Docket createRestApi() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .select()
	        // 指定哪些API被Swagger扫描
	        .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
	        .paths(PathSelectors.any())
	        .build()
	        .apiInfo(this.apiInfo());
	  }
}
