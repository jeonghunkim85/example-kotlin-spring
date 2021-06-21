package com.deliwind.kotlinstudy.commons

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.OAS_30) // open api spec 3.0
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController::class.java))
        .paths(PathSelectors.any())
        .build()
}
