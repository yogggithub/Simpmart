package com.simpmart.order.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.order.entity.OrderOperateHistoryEntity;
import com.simpmart.order.service.OrderOperateHistoryService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * order operate history
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:35
 */
@RestController
@RequestMapping("order/orderoperatehistory")
public class OrderOperateHistoryController {
    @Autowired
    private OrderOperateHistoryService orderOperateHistoryService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("order:orderoperatehistory:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderOperateHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("order:orderoperatehistory:info")
    public R info(@PathVariable("id") Long id) {
            OrderOperateHistoryEntity orderOperateHistory = orderOperateHistoryService.getById(id);

        return R.ok().put("orderOperateHistory", orderOperateHistory);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("order:orderoperatehistory:save")
    public R save(@RequestBody OrderOperateHistoryEntity orderOperateHistory) {
            orderOperateHistoryService.save(orderOperateHistory);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("order:orderoperatehistory:update")
    public R update(@RequestBody OrderOperateHistoryEntity orderOperateHistory) {
            orderOperateHistoryService.updateById(orderOperateHistory);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("order:orderoperatehistory:delete")
    public R delete(@RequestBody Long[] ids) {
            orderOperateHistoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
