package com.simpmart.warehouse.controller;

import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;
import com.simpmart.warehouse.entity.PurchaseEntity;
import com.simpmart.warehouse.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * purchase information
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:01
 */
@RestController
@RequestMapping("warehouse/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("warehouse:purchase:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("warehouse:purchase:info")
    public R info(@PathVariable("id") Long id) {
            PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("warehouse:purchase:save")
    public R save(@RequestBody PurchaseEntity purchase) {
            purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("warehouse:purchase:update")
    public R update(@RequestBody PurchaseEntity purchase) {
            purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("warehouse:purchase:delete")
    public R delete(@RequestBody Long[] ids) {
            purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
