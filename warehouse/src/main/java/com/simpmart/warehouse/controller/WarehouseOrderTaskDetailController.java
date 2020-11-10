package com.simpmart.warehouse.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.warehouse.entity.WarehouseOrderTaskDetailEntity;
import com.simpmart.warehouse.service.WarehouseOrderTaskDetailService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * ware task detail
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@RestController
@RequestMapping("warehouse/warehouseordertaskdetail")
public class WarehouseOrderTaskDetailController {
    @Autowired
    private WarehouseOrderTaskDetailService warehouseOrderTaskDetailService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("warehouse:warehouseordertaskdetail:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = warehouseOrderTaskDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("warehouse:warehouseordertaskdetail:info")
    public R info(@PathVariable("id") Long id) {
            WarehouseOrderTaskDetailEntity warehouseOrderTaskDetail = warehouseOrderTaskDetailService.getById(id);

        return R.ok().put("warehouseOrderTaskDetail", warehouseOrderTaskDetail);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("warehouse:warehouseordertaskdetail:save")
    public R save(@RequestBody WarehouseOrderTaskDetailEntity warehouseOrderTaskDetail) {
            warehouseOrderTaskDetailService.save(warehouseOrderTaskDetail);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("warehouse:warehouseordertaskdetail:update")
    public R update(@RequestBody WarehouseOrderTaskDetailEntity warehouseOrderTaskDetail) {
            warehouseOrderTaskDetailService.updateById(warehouseOrderTaskDetail);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("warehouse:warehouseordertaskdetail:delete")
    public R delete(@RequestBody Long[] ids) {
            warehouseOrderTaskDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
