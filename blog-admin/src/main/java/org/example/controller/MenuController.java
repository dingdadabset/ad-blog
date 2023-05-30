package org.example.controller;

import com.sun.org.apache.regexp.internal.RE;
import org.example.conf.ResponseResult;
import org.example.entity.Menu;
import org.example.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.controller
 * @Author: dingquan
 * @CreateTime: 2023-05-29  19:24
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("system/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @GetMapping("list")
    public ResponseResult<List<Menu>> selectList(String status,String menuName){
     return   menuService.selectList(status,menuName);
    }
    @GetMapping("{id}")
    public ResponseResult selMenu(@PathVariable String id){
        return ResponseResult.okResult(menuService.getById(id)  );
    }
    @PostMapping()
    public ResponseResult addMenu(@RequestBody Menu menu){
    return ResponseResult.okResult(menuService.save(menu));
    }
    @DeleteMapping("{menuId}")
    public ResponseResult delMenu(@PathVariable String menuId){
        return ResponseResult.okResult(menuService.removeById(menuId));
    }
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){

        return ResponseResult.okResult(menuService.updateById(menu));
    }
    @GetMapping("treeselect")
    public ResponseResult treeSelect(){
       //TODO 这里还没写
        return  null;
    }
    @GetMapping("roleMenuTreeselect/{id}")
    public ResponseResult getTreeById(@PathVariable String id){

     return    menuService.getTreeById(id);
    }



}
