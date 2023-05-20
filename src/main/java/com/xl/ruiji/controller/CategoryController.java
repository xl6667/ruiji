package com.xl.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xl.ruiji.pojo.Category;
import com.xl.ruiji.pojo.R;
import com.xl.ruiji.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @PostMapping
    public R<String> saveCategory(@RequestBody Category category){
        log.info("添加套餐");
        categoryService.save(category);
        return R.success("添加成功");
    }
    @GetMapping("/page")
    public R<Page> page( int page,int pageSize){
        Page<Category> page1 = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Category::getSort);
        categoryService.page(page1);
        return R.success(page1);
    }
    @DeleteMapping
    public R<String> delete(String ids){

       categoryService.move(ids);
        return R.success("删除成功");
    }
    @PutMapping
    public R<String> put(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
