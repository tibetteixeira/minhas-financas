package io.github.tibetteixeira.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    private static final String HEADER = "header";
    private static final String JWT = "JWT";
    private static final String GLOBAL = "global";
    private static final String ACCESS_EVERYTHING = "accessEverything";
    private static final String CONTROLLER_PACKAGE = "io.github.tibetteixeira.api.v1.controller";

    @Value("${info.contact.name}")
    private String name;

    @Value("${info.contact.email}")
    private String email;

    @Value("${info.contact.link}")
    private String link;

    @Value("${info.app.title}")
    private String title;

    @Value("${info.app.description}")
    private String description;

    @Value("${info.app.version}")
    private String version;


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(CONTROLLER_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(singletonList(securityContext()))
                .securitySchemes(singletonList(apiKey()))
                .apiInfo(apiInfo());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/docs", "/swagger-ui/index.html");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .contact(contact())
                .build();
    }

    private Contact contact() {
        return new Contact(name, link, email);
    }

    public ApiKey apiKey() {
        return new ApiKey(JWT, SecurityConfig.getAuthorization(), HEADER);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(GLOBAL, ACCESS_EVERYTHING);
        AuthorizationScope[] scopes = new AuthorizationScope[]{authorizationScope};
        SecurityReference reference = new SecurityReference(JWT, scopes);

        return List.of(reference);
    }
}
