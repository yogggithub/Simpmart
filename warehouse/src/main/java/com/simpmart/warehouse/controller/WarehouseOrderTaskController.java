package com.simpmart.warehouse.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.warehouse.entity.WarehouseOrderTaskEntity;
import com.simpmart.warehouse.service.WarehouseOrderTaskService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * ware task
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@RestController
@RequestMapping("warehouse/warehouseordertask")
public class WarehouseOrderTaskController {
    @Autowired
    private WarehouseOrderTaskService warehouseOrderTaskService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("warehouse:warehouseordertask:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = warehouseOrderTaskService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("warehouse:warehouseordertask:info")
    public R info(@PathVariable("id") Long id) {
            WarehouseOrderTaskEntity warehouseOrderTask = warehouseOrderTaskService.getById(id);

        return R.ok().put("warehouseOrderTask", warehouseOrderTask);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("warehouse:warehouseordertask:save")
    public R save(@RequestBody WarehouseOrderTaskEntity warehouseOrderTask) {
            warehouseOrderTaskService.save(warehouseOrderTask);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("warehouse:warehouseordertask:update")
    public R update(@RequestBody WarehouseOrderTaskEntity warehouseOrderTask) {
            warehouseOrderTaskService.updateById(warehouseOrderTask);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("warehouse:warehouseordertask:delete")
    public R delete(@RequestBody Long[] ids) {
            warehouseOrderTaskService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
