package com.roche.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("FizzBuzz Server APIs")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.roche.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo("Roche: FizzBuzz APIs"));
    }

    private ApiInfo apiInfo(String title) {
       ApiInfo apiInfo= new ApiInfoBuilder()
           .title(title)
           .description("FizzBuzz Service for Roche")
           .version("FizzBuzz v1")
           .build();
        return apiInfo;
    }
}
