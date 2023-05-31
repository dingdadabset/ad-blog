package org.example.controller;

import org.example.conf.ResponseResult;
import org.example.entity.SgLink;
import org.example.service.impl.LinkListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.controller
 * @Author: dingquan
 * @CreateTime: 2023-05-31  17:22
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/content/link")
public class LinkListController {
    @Autowired
    private LinkListServiceImpl linkListService;
    @GetMapping("list")
    public ResponseResult getList(Integer pageNum, Integer pageSize, String name, String status){
        return linkListService.getList(pageNum,pageSize,name,status);
    }
    @DeleteMapping("{id}")
    public ResponseResult delUserById(@PathVariable String id){
        return ResponseResult.okResult(linkListService.removeById(id));
    }

    @GetMapping("{id}")
    public ResponseResult getOneSgLinkById(@PathVariable String id){
        return ResponseResult.okResult(linkListService.getById(id));
    }
    @PutMapping ()
    public ResponseResult updateLink(@RequestBody SgLink user){
        return ResponseResult.okResult(linkListService.updateById(user));
    }
    @PostMapping ()
    public ResponseResult addLink(@RequestBody SgLink user){
        return ResponseResult.okResult(linkListService.save(user));
    }
}
