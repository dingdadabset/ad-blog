package org.example.conf;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 * @description:具体来说，该处理器会在插入数据时，自动填充createTime、createBy、updateTime和updateBy字段的值。
 * 其中，createTime和createBy表示数据的创建时间和创建人，updateTime和updateBy表示数据的更新时间和更新人。
 * 填充的值分别为当前时间和当前登录用户的ID，如果获取不到登录用户的ID，则填充为-1表示是自己创建的。
 * @author: dingquan
 * @date: 2023/6/12 14:38
 * @param:
 * @param: null
 * @return: null
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;//表示是自己创建
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName(" ", SecurityUtils.getUserId(), metaObject);
    }
}