package org.example.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.example.conf.AppHttpCodeEnum;
import org.example.conf.ResponseResult;
import org.example.conf.WebUtils;
import org.example.entity.Category;
import org.example.entity.CategoryVo;
import org.example.entity.CategoryVo2;
import org.example.entity.ExcelCategoryVo;
import org.example.service.CategoryService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo2> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
    @GetMapping("list")
    public ResponseResult getList(Integer pageNum,Integer pageSize,String name, String status){

        return categoryService.getCategoryPageList(pageNum,pageSize,name,status);
    }
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category){

        return ResponseResult.okResult(categoryService.save(category));
    }
    @DeleteMapping("{id}")
    public ResponseResult delCategory(@PathVariable String id){

        return ResponseResult.okResult(categoryService.removeById(id));
    }
    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category){

        return ResponseResult.okResult(categoryService.saveOrUpdate(category));
    }
    @GetMapping ("{id}")
    public ResponseResult selCategory(@PathVariable String id){

        return ResponseResult.okResult(categoryService.getById(id));
    }



}
