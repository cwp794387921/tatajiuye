package com.tata.jiuye.portal.config;

import com.tata.jiuye.common.config.BaseSwaggerConfig;
import com.tata.jiuye.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2API文档的配置
 * Created by macro on 2018/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.tata.jiuye.portal.controller")
                .title("塔塔酒业前台系统")
                .description("塔塔酒业前台相关接口文档")
                .contactName("塔塔酒业")
                .version("V1.0.0")
                .enableSecurity(true)
                .build();
    }
}
