package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.lang.Strings;
import org.example.conf.ResponseResult;
import org.example.conf.SystemConstants;
import org.example.entity.LinkListsVo;
import org.example.entity.SgLink;
import org.example.entity.SgLink;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.service.impl
 * @Author: dingquan
 * @CreateTime: 2023-05-31  17:23
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class LinkListServiceImpl extends SgLinkServiceImpl{
    public ResponseResult getList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<SgLink> eq = new LambdaQueryWrapper<SgLink>()
                .eq(Strings.hasText(name), SgLink::getName, name)
                .eq(SgLink::getStatus, status);

        Page<SgLink> objectPage = new Page<>();
        objectPage.setCurrent(pageNum);
        objectPage.setSize(pageSize);
        page(objectPage,eq);
        return ResponseResult.okResult(new LinkListsVo(objectPage.getRecords(),objectPage.getTotal()));
    }
}
