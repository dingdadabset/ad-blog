package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.conf.ResponseResult;
import org.example.conf.SystemConstants;
import org.example.dao.SgLinkDao;
import org.example.entity.LinkVo;
import org.example.entity.SgLink;
import org.example.service.SgLinkService;
import org.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(SgLink)表服务实现类
 *
 * @author makejava
 * @since 2023-05-23 15:26:55
 */
@Service("sgLinkService")
public class SgLinkServiceImpl extends ServiceImpl<SgLinkDao, SgLink> implements SgLinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<SgLink> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SgLink::getStatus, SystemConstants.LINK_STATUS_NORMAL);

        List<SgLink> links = list(queryWrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回

        return ResponseResult.okResult(linkVos);
    }

}

