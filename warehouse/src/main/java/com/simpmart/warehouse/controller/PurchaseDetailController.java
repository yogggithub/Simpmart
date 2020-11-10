package com.simpmart.warehouse.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.warehouse.entity.PurchaseDetailEntity;
import com.simpmart.warehouse.service.PurchaseDetailService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * purchase detail
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@RestController
@RequestMapping("warehouse/purchasedetail")
public class PurchaseDetailController {
    @Autowired
    private PurchaseDetailService purchaseDetailService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("warehouse:purchasedetail:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("warehouse:purchasedetail:info")
    public R info(@PathVariable("id") Long id) {
            PurchaseDetailEntity purchaseDetail = purchaseDetailService.getById(id);

        return R.ok().put("purchaseDetail", purchaseDetail);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("warehouse:purchasedetail:save")
    public R save(@RequestBody PurchaseDetailEntity purchaseDetail) {
            purchaseDetailService.save(purchaseDetail);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("warehouse:purchasedetail:update")
    public R update(@RequestBody PurchaseDetailEntity purchaseDetail) {
            purchaseDetailService.updateById(purchaseDetail);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("warehouse:purchasedetail:delete")
    public R delete(@RequestBody Long[] ids) {
            purchaseDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
