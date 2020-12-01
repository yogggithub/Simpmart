package com.simpmart.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.commodity.dao.SpuImagesDao;
import com.simpmart.commodity.entity.SpuImagesEntity;
import com.simpmart.commodity.service.SpuImagesService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long id, List<String> images) {
        if (images != null && images.size() > 0) {
            List<SpuImagesEntity> imagesEntityList =
                    images.stream().map(img -> {
                        SpuImagesEntity imagesEntity = new SpuImagesEntity();
                        imagesEntity.setSpuId(id);
                        imagesEntity.setImgUrl(img);
                        return imagesEntity;
                    }).collect(Collectors.toList());
            this.saveBatch(imagesEntityList);
        }
    }

}