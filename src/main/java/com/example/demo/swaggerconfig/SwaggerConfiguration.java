package com.example.demo.swaggerconfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	
	
	private final String AUTHENTICATION_HEADER = "Authorization";
	
	@Autowired
	private SwaggerPropertyDetails swagger;

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apikey())).select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demo")).paths(PathSelectors.any()).build();
	}

	private ApiKey apikey() {

		return new ApiKey("JWT", AUTHENTICATION_HEADER, "header");
	}

	private ApiInfo apiInfo() {

		return new ApiInfo(swagger.getTitle(),
				swagger.getDoc(),swagger.getVersion(), swagger.getTermsOfService(),
				new Contact(swagger.getUsername(), swagger.getWebsite(), swagger.getEmail()), swagger.getLicense(),
				swagger.getLicenseurl(), Collections.emptyList());
	}

	private SecurityContext securityContext() {

		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}

}
