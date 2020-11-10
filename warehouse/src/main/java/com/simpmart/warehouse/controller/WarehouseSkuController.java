package com.simpmart.warehouse.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.warehouse.entity.WarehouseSkuEntity;
import com.simpmart.warehouse.service.WarehouseSkuService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * production inventory
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@RestController
@RequestMapping("warehouse/warehousesku")
public class WarehouseSkuController {
    @Autowired
    private WarehouseSkuService warehouseSkuService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("warehouse:warehousesku:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = warehouseSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("warehouse:warehousesku:info")
    public R info(@PathVariable("id") Long id) {
            WarehouseSkuEntity warehouseSku = warehouseSkuService.getById(id);

        return R.ok().put("warehouseSku", warehouseSku);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("warehouse:warehousesku:save")
    public R save(@RequestBody WarehouseSkuEntity warehouseSku) {
            warehouseSkuService.save(warehouseSku);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("warehouse:warehousesku:update")
    public R update(@RequestBody WarehouseSkuEntity warehouseSku) {
            warehouseSkuService.updateById(warehouseSku);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("warehouse:warehousesku:delete")
    public R delete(@RequestBody Long[] ids) {
            warehouseSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
