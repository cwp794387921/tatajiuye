package com.tata.jiuye.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.tata.jiuye")
public class JiuyePortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiuyePortalApplication.class, args);
    }

}
