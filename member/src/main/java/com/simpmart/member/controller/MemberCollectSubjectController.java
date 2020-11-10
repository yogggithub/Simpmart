package com.simpmart.member.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.member.entity.MemberCollectSubjectEntity;
import com.simpmart.member.service.MemberCollectSubjectService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * member favorite topic
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:15
 */
@RestController
@RequestMapping("member/membercollectsubject")
public class MemberCollectSubjectController {
    @Autowired
    private MemberCollectSubjectService memberCollectSubjectService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:membercollectsubject:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberCollectSubjectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:membercollectsubject:info")
    public R info(@PathVariable("id") Long id) {
            MemberCollectSubjectEntity memberCollectSubject = memberCollectSubjectService.getById(id);

        return R.ok().put("memberCollectSubject", memberCollectSubject);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:membercollectsubject:save")
    public R save(@RequestBody MemberCollectSubjectEntity memberCollectSubject) {
            memberCollectSubjectService.save(memberCollectSubject);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:membercollectsubject:update")
    public R update(@RequestBody MemberCollectSubjectEntity memberCollectSubject) {
            memberCollectSubjectService.updateById(memberCollectSubject);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:membercollectsubject:delete")
    public R delete(@RequestBody Long[] ids) {
            memberCollectSubjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
