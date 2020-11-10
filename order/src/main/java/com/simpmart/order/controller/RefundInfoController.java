package com.simpmart.order.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.order.entity.RefundInfoEntity;
import com.simpmart.order.service.RefundInfoService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * refund information
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:35
 */
@RestController
@RequestMapping("order/refundinfo")
public class RefundInfoController {
    @Autowired
    private RefundInfoService refundInfoService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:refundinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = refundInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("order:refundinfo:info")
    public R info(@PathVariable("id") Long id) {
            RefundInfoEntity refundInfo = refundInfoService.getById(id);

        return R.ok().put("refundInfo", refundInfo);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:refundinfo:save")
    public R save(@RequestBody RefundInfoEntity refundInfo) {
            refundInfoService.save(refundInfo);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("order:refundinfo:update")
    public R update(@RequestBody RefundInfoEntity refundInfo) {
            refundInfoService.updateById(refundInfo);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("order:refundinfo:delete")
    public R delete(@RequestBody Long[] ids) {
            refundInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
