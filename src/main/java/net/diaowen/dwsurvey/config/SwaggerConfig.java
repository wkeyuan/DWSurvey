package net.diaowen.dwsurvey.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /*配置多个分组，只需要配置多个Docket即可，设置不同分组扫描不同的包*/
    /**
     * 基于token认证的API信息，适用于前端研发来使用
     * @return
     */
    @Bean
    public Docket tokenDocket() {
        return new Docket(DocumentationType.OAS_30).apiInfo(tokenApiInfo()).select().
                apis(RequestHandlerSelectors.basePackage("net.diaowen.dwsurvey.controller").and(RequestHandlerSelectors.withClassAnnotation(Api.class))).
                paths(PathSelectors.any()).build().groupName("SYSTEM-API").
                securitySchemes(securitySchemes()).
                securityContexts(securityContexts());
//                        .globalRequestParameters(getTokenGlobalRequestParameters());
    }

    private ApiInfo tokenApiInfo() {
        Contact contact = new Contact("调问", "www.diaowen.net", "service@diaowen.net");
        return new ApiInfo(
                "调问-系统API文档",
                "专心做好一款产品（基于token认证，适合前端使用）",
                "6.0",
                "urn:tos",
                contact,
                "",
                "",
                new ArrayList<>());
    }

    private List<RequestParameter> getTokenGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("Authorization")
                .description("用户的认证TOKEN")
                .required(true)
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
        return parameters;
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> newList = new ArrayList<>();
        newList.add(new ApiKey("Authorization", "Authorization", "header"));
        return newList;
    }
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> newList = new ArrayList<>();
        newList.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
        return newList;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> newList = new ArrayList<>();
        newList.add(new SecurityReference("Authorization", authorizationScopes));
        return newList;
    }
}
