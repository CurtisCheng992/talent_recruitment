package com.curtis.talent_recruitment.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Curtis
 * @version 1.0.0
 * @description 人才招聘系统api文档配置
 * @date 2021/2/23 10:15
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi() {
        return new Docket( DocumentationType.SWAGGER_2 )
                .apiInfo( apiInfo() )
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.curtis" ) )
                .paths( PathSelectors.any() )
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title( "人才招聘系统api文档" )
                .description( "人才招聘系统api文档" )
                .version( "1.0.0" )
                .build();
    }

}
