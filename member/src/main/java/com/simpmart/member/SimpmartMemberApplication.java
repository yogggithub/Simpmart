package com.simpmart.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(
		basePackages = "com.simpmart.member.feign"
)
@MapperScan("com.simpmart.member.dao")
public class SimpmartMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpmartMemberApplication.class, args);
	}

}
