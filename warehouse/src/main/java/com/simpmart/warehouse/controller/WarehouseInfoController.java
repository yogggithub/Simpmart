package com.simpmart.warehouse.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.warehouse.entity.WarehouseInfoEntity;
import com.simpmart.warehouse.service.WarehouseInfoService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * warehouse information
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@RestController
@RequestMapping("warehouse/warehouseinfo")
public class WarehouseInfoController {
    @Autowired
    private WarehouseInfoService warehouseInfoService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("warehouse:warehouseinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = warehouseInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("warehouse:warehouseinfo:info")
    public R info(@PathVariable("id") Long id) {
            WarehouseInfoEntity warehouseInfo = warehouseInfoService.getById(id);

        return R.ok().put("warehouseInfo", warehouseInfo);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("warehouse:warehouseinfo:save")
    public R save(@RequestBody WarehouseInfoEntity warehouseInfo) {
            warehouseInfoService.save(warehouseInfo);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("warehouse:warehouseinfo:update")
    public R update(@RequestBody WarehouseInfoEntity warehouseInfo) {
            warehouseInfoService.updateById(warehouseInfo);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("warehouse:warehouseinfo:delete")
    public R delete(@RequestBody Long[] ids) {
            warehouseInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
