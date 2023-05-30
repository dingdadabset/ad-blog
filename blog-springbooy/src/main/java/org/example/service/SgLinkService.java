package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.conf.ResponseResult;
import org.example.entity.SgLink;

/**
 * 友链(SgLink)表服务接口
 *
 * @author makejava
 * @since 2023-05-23 15:26:55
 */
public interface SgLinkService extends IService<SgLink> {

    ResponseResult getAllLink();
}

