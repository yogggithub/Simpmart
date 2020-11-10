package com.simpmart.commodity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.simpmart.commodity.dao")
@SpringBootApplication
public class SimpmartCommodityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpmartCommodityApplication.class, args);
	}

}
