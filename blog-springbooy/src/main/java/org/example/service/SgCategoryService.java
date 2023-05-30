package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.conf.ResponseResult;
import org.example.entity.SgCategory;

/**
 * 分类表(SgCategory)表服务接口
 *
 * @author makejava
 * @since 2023-05-22 18:33:50
 */
public interface SgCategoryService extends IService<SgCategory> {

    ResponseResult getCategoryList();
}

