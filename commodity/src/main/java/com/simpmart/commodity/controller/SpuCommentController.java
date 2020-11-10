package com.simpmart.commodity.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.commodity.entity.SpuCommentEntity;
import com.simpmart.commodity.service.SpuCommentService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * product review
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/spucomment")
public class SpuCommentController {
    @Autowired
    private SpuCommentService spuCommentService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:spucomment:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuCommentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:spucomment:info")
    public R info(@PathVariable("id") Long id) {
            SpuCommentEntity spuComment = spuCommentService.getById(id);

        return R.ok().put("spuComment", spuComment);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:spucomment:save")
    public R save(@RequestBody SpuCommentEntity spuComment) {
            spuCommentService.save(spuComment);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:spucomment:update")
    public R update(@RequestBody SpuCommentEntity spuComment) {
            spuCommentService.updateById(spuComment);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:spucomment:delete")
    public R delete(@RequestBody Long[] ids) {
            spuCommentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
