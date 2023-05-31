package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.example.conf.ResponseResult;
import org.example.conf.SystemConstants;
import org.example.entity.CategoryVo;
import org.example.mapper.CategoryMapper;
import org.example.entity.Category;
import org.example.service.CategoryService;
import org.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-05-29 16:15:08
 */
@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public ResponseResult getCategoryList() {
        return null;
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        return BeanCopyUtils.copyBeanList(list, CategoryVo.class);
    }

    @Override
    public ResponseResult getCategoryPageList(Integer pageNum, Integer pageSize, Category category) {

        LambdaQueryWrapper<Category> eq = new LambdaQueryWrapper<Category>().eq(Strings.hasText(category.getName()), Category::getName, category.getName())
                .eq(Strings.hasText(category.getStatus()), Category::getStatus, category.getStatus())
                .or().eq(Category::getStatus,SystemConstants.NORMAL);

        Page<Category> objectPage = new Page<>();
        objectPage.setCurrent(pageNum);
        objectPage.setSize(pageSize);
        page(objectPage,eq);
        return ResponseResult.okResult(new CategoryVo(objectPage.getRecords(),objectPage.getTotal()));
    }
}

