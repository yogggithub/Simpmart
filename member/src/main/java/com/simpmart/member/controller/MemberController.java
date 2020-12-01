package com.simpmart.member.controller;

import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;
import com.simpmart.member.entity.MemberEntity;
import com.simpmart.member.feign.CouponFeignService;
import com.simpmart.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * member
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:16
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id) {
            MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member) {
            memberService.save(member);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member) {
            memberService.updateById(member);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids) {
            memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * Test OpenFeign function
     * import the interface created for calling OpenFeign
     */
    @Autowired
    CouponFeignService couponFeignService;

    /**
     * Test OpenFeign function
     * @return
     */
    @RequestMapping("/coupons")
    public R TestOpenFeign(){
        // For test purpose, create a member entity
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("Dave");

        // Call the interface and get response
        R memberCoupon = couponFeignService.memberCoupon();

        // Response with member info and coupons list
        return R.ok()
                .put("member", memberEntity.getNickname())
                .put("coupons", memberCoupon.get("coupons"));
    }

}
