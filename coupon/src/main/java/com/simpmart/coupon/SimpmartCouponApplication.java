package com.simpmart.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.simpmart.coupon.dao")
public class SimpmartCouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpmartCouponApplication.class, args);
	}

}
