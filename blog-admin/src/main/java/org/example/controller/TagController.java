package org.example.controller;




import org.example.conf.ResponseResult;
import org.example.entity.PageVo;
import org.example.entity.Tag;
import org.example.entity.TagListDto;
import org.example.entity.TagVo;
import org.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 标签(Tag)表控制层
 *
 * @author makejava
 * @since 2023-05-26 14:19:30
 */
@RestController
@RequestMapping("/content/tag")
public class TagController  {
    /**
     * 服务对象
     */
    @Resource
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }
    @PostMapping ("")
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.tagAdd(tagListDto);
    }
    @PutMapping  ("")
    public ResponseResult updateTag(@RequestBody TagListDto tagListDto){

        return tagService.updateTag(tagListDto);
    }
    @DeleteMapping("{id}")
    public ResponseResult delTag(@PathVariable Integer id){
        return tagService.tagDel(id);
    }
    @GetMapping ("{id}")
    public ResponseResult selectTagById(@PathVariable Integer id) {
        return ResponseResult.okResult(tagService.getById(id));
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }

        //@GetMapping("/list")
    //public ResponseResult list(){
    //    return ResponseResult.okResult(tagService.list());
    //}
    ///**
    // * 分页查询所有数据
    // *
    // * @param page 分页对象
    // * @param tag 查询实体
    // * @return 所有数据
    // */
    //@GetMapping
    //public R selectAll(Page<Tag> page, Tag tag) {
    //    return success(this.tagService.page(page, new QueryWrapper<>(tag)));
    //}
    //
    ///**
    // * 通过主键查询单条数据
    // *
    // * @param id 主键
    // * @return 单条数据
    // */
    //@GetMapping("{id}")
    //public R selectOne(@PathVariable Serializable id) {
    //    return success(this.tagService.getById(id));
    //}
    //
    ///**
    // * 新增数据
    // *
    // * @param tag 实体对象
    // * @return 新增结果
    // */
    //@PostMapping
    //public R insert(@RequestBody Tag tag) {
    //    return success(this.tagService.save(tag));
    //}
    //
    ///**
    // * 修改数据
    // *
    // * @param tag 实体对象
    // * @return 修改结果
    // */
    //@PutMapping
    //public R update(@RequestBody Tag tag) {
    //    return success(this.tagService.updateById(tag));
    //}
    //
    ///**
    // * 删除数据
    // *
    // * @param idList 主键结合
    // * @return 删除结果
    // */
    //@DeleteMapping
    //public R delete(@RequestParam("idList") List<Long> idList) {
    //    return success(this.tagService.removeByIds(idList));
    //}
}

