package com.tata.jiuye.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 *
 * @author lewis
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.tata.jiuye.mapper", "com.tata.jiuye.dao"})
public class MyBatisConfig {

}
