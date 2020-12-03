package com.simpmart.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.warehouse.entity.PurchaseEntity;
import com.simpmart.warehouse.vo.MergeVo;
import com.simpmart.warehouse.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * purchase information
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:01
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceived(Map<String, Object> params);

    void merge(MergeVo mergeVo);

    void receiveTask(List<Long> ids);

    void purchaseDone(PurchaseDoneVo vo);
}

