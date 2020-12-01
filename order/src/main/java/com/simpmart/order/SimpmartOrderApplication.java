package com.simpmart.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.simpmart.order.dao")
public class SimpmartOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpmartOrderApplication.class, args);
	}

}
