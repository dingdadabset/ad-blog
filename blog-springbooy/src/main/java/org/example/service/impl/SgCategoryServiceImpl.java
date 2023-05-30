package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.example.conf.ResponseResult;
import org.example.conf.SystemConstants;
import org.example.dao.SgCategoryDao;
import org.example.entity.SgArticle;
import org.example.entity.SgCategory;
import org.example.entity.SgCategoryVO;
import org.example.service.SgArticleService;
import org.example.service.SgCategoryService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(SgCategory)表服务实现类
 *
 * @author makejava
 * @since 2023-05-22 18:33:50
 */
@Service("sgCategoryService")
public class SgCategoryServiceImpl extends ServiceImpl<SgCategoryDao, SgCategory> implements SgCategoryService {
        @Autowired
        private SgArticleService sgArticleService;
    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<SgArticle> eq = new LambdaQueryWrapper<SgArticle>().eq(SgArticle::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        List<SgArticle> list = sgArticleService.list(eq);
        Set<Long> categouryId = list.stream().map(x -> x.getCategoryId()).collect(Collectors.toSet());

        List<SgCategory> sgCategories = listByIds(categouryId);
        List<SgCategory> collect = sgCategories.stream().filter(x -> x.getStatus().equals(SystemConstants.ARTICLE_STATUS_NORMAL)).collect(Collectors.toList());
        List<SgCategoryVO> categoryVos = BeanCopyUtils.copyBeanList(collect, SgCategoryVO.class);

        return ResponseResult.okResult(categoryVos);

    }
}

