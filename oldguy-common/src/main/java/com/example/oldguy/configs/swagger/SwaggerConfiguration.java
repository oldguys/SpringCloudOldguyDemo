package com.example.oldguy.configs.swagger;

import com.example.oldguy.utils.Log4jUtils;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @ClassName: SwaggerConfiguration
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/17 0017 下午 12:30
 **/
@EnableSwaggerBootstrapUI
@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/doc", "/api/doc.htm").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/static/swagger/");
    }


    @Bean
    public Docket createRestApi() {
        Log4jUtils.getInstance(getClass()).info("启动 swagger 文档");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.example.oldguy"))
                .paths(PathSelectors.any())
                .build();
//            .securitySchemes(security());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(appName)
                .build();
    }

}
