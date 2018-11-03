package com.caogen.blog;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ConditionalOnExpression("${swagger.enable}")
public class SwaggerConfig {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot APIs",
                "Spring Boot + Swagger2",
                "1.0.0",
                null,
                "康草根的博客",
                "作者：康草根",
                "https://kcaogen.com/");
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(regex("/api/.*"))
                .build()
                .apiInfo(apiInfo)
                .useDefaultResponseMessages(false);
        return docket;
    }

}
