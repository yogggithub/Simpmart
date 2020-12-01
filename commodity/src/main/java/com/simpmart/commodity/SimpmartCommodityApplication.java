package com.simpmart.commodity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.simpmart.commodity.dao")
@EnableFeignClients(basePackages = "com.simpmart.commodity.feign")
public class SimpmartCommodityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpmartCommodityApplication.class, args);
	}

}
