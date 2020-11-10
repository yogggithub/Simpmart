package com.simpmart.coupon.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.coupon.entity.SeckillSkuNoticeEntity;
import com.simpmart.coupon.service.SeckillSkuNoticeService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * seckill subcribe
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@RestController
@RequestMapping("coupon/seckillskunotice")
public class SeckillSkuNoticeController {
    @Autowired
    private SeckillSkuNoticeService seckillSkuNoticeService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:seckillskunotice:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = seckillSkuNoticeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:seckillskunotice:info")
    public R info(@PathVariable("id") Long id) {
            SeckillSkuNoticeEntity seckillSkuNotice = seckillSkuNoticeService.getById(id);

        return R.ok().put("seckillSkuNotice", seckillSkuNotice);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:seckillskunotice:save")
    public R save(@RequestBody SeckillSkuNoticeEntity seckillSkuNotice) {
            seckillSkuNoticeService.save(seckillSkuNotice);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:seckillskunotice:update")
    public R update(@RequestBody SeckillSkuNoticeEntity seckillSkuNotice) {
            seckillSkuNoticeService.updateById(seckillSkuNotice);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:seckillskunotice:delete")
    public R delete(@RequestBody Long[] ids) {
            seckillSkuNoticeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
