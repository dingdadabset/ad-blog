package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.conf.ResponseResult;
import org.example.entity.PageVo;
import org.example.entity.Tag;
import org.example.entity.TagListDto;
import org.example.entity.TagVo;
import org.example.mapper.TagMapper;
import org.example.service.TagService;
import org.example.utils.BeanCopyUtils;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-05-26 14:19:30
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult tagAdd(TagListDto tagListDto) {
        Tag tag = new Tag();
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());

        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult tagDel(Integer id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateTag(TagListDto tagListDto) {
        Tag byId = getById(tagListDto.getId());
        Tag build = Tag.builder().name(tagListDto.getName()).remark(tagListDto.getRemark())
                .id(tagListDto.getId())
                .createTime(byId.getCreateTime())
                .updateTime(new Date()).build();
        save(build);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }
}

