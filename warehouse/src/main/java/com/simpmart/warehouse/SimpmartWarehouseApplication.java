package com.simpmart.warehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.simpmart.warehouse.dao")
@EnableFeignClients
public class SimpmartWarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpmartWarehouseApplication.class, args);
	}

}
