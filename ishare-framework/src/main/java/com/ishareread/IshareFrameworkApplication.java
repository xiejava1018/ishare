package com.ishareread;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动程序
 */
@EnableTransactionManagement
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class  })
@MapperScan("com.ishareread.project.*.*.mapper")
public class IshareFrameworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(IshareFrameworkApplication.class, args);
	}
}
