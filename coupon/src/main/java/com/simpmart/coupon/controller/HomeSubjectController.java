package com.simpmart.coupon.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.coupon.entity.HomeSubjectEntity;
import com.simpmart.coupon.service.HomeSubjectService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * home page topic
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@RestController
@RequestMapping("coupon/homesubject")
public class HomeSubjectController {
    @Autowired
    private HomeSubjectService homeSubjectService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:homesubject:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = homeSubjectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:homesubject:info")
    public R info(@PathVariable("id") Long id) {
            HomeSubjectEntity homeSubject = homeSubjectService.getById(id);

        return R.ok().put("homeSubject", homeSubject);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:homesubject:save")
    public R save(@RequestBody HomeSubjectEntity homeSubject) {
            homeSubjectService.save(homeSubject);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:homesubject:update")
    public R update(@RequestBody HomeSubjectEntity homeSubject) {
            homeSubjectService.updateById(homeSubject);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:homesubject:delete")
    public R delete(@RequestBody Long[] ids) {
            homeSubjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
