package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.conf.ResponseResult;
import org.example.entity.PageVo;
import org.example.entity.Tag;
import org.example.entity.TagListDto;
import org.example.entity.TagVo;

import java.util.List;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-05-26 14:19:30
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult tagAdd(TagListDto tagListDto);

    ResponseResult tagDel(Integer id);

    ResponseResult updateTag(TagListDto tagListDto);

    List<TagVo> listAllTag();
}

