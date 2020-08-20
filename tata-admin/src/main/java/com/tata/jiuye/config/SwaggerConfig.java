package com.tata.jiuye.config;

import com.tata.jiuye.common.config.BaseSwaggerConfig;
import com.tata.jiuye.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 *
 * @author lewis
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.tata.jiuye.controller")
                .title("塔塔酒业")
                .description("接口文档")
                .contactName("塔塔酒业")
                .version("V1.0.0")
                .enableSecurity(true)
                .build();
    }
}
