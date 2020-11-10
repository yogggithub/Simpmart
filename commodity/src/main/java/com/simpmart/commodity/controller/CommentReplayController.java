package com.simpmart.commodity.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.commodity.entity.CommentReplayEntity;
import com.simpmart.commodity.service.CommentReplayService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * review & reply relationship
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/commentreplay")
public class CommentReplayController {
    @Autowired
    private CommentReplayService commentReplayService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:commentreplay:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = commentReplayService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:commentreplay:info")
    public R info(@PathVariable("id") Long id) {
            CommentReplayEntity commentReplay = commentReplayService.getById(id);

        return R.ok().put("commentReplay", commentReplay);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:commentreplay:save")
    public R save(@RequestBody CommentReplayEntity commentReplay) {
            commentReplayService.save(commentReplay);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:commentreplay:update")
    public R update(@RequestBody CommentReplayEntity commentReplay) {
            commentReplayService.updateById(commentReplay);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:commentreplay:delete")
    public R delete(@RequestBody Long[] ids) {
            commentReplayService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
