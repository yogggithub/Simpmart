package com.simpmart.commodity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simpmart.commodity.entity.BrandEntity;
import com.simpmart.commodity.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SimpmartCommodityApplicationTests {
	@Autowired
	BrandService brandService;

	@Test
	void contextLoads() {
		/*
		 * insert one new record with brand name Acer
		 * because the ID is auto incremental
		 * so no need to set ID of the new brand entity
		 */
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setName("Acer");
		brandService.save(brandEntity);

		/*
		 * update added record
		 * note: ID is long type
		 */
		brandEntity.setBrandId(1l);
		brandEntity.setDescript("Acer");
		brandService.updateById(brandEntity);

		// Inquiry and display a record
		System.out.println(brandService.list(
				/*
				 * QueryWrapper<E>(): E must the same as the result type
				 *
				 * like():
				 * first parameter is the column name
				 * second parameter is the argument that want to searching
				 */
				new QueryWrapper<BrandEntity>()
				.like("name","a%")));
	}

}
