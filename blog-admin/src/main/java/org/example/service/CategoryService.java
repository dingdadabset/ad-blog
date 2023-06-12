package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.conf.ResponseResult;
import org.example.entity.Category;
import org.example.entity.CategoryVo2;

import java.util.List;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-05-29 16:15:08
 */
public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    List<CategoryVo2> listAllCategory();

    ResponseResult getCategoryPageList(Integer pageNum,Integer pageSize,String name, String status);
}

