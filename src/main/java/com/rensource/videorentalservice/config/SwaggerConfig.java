/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rensource.videorentalservice.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author austine.okoroafor
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket api()
	{
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.rensource.videorentalservice.controller"))
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());                                           
    }
	
	private ApiInfo apiInfo()
	{
	    return new ApiInfo(
	      "rensource - video-rental-service", 
	      "video-rental-service", 
	      "API TOS", 
	      "Terms of service", 
	      new Contact("Austine Okoroafor", "www.clan.com", "okoroaforaustine@gmail.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
    
}
