package com.xl.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xl.ruiji.dto.SetmealDto;
import com.xl.ruiji.pojo.*;
import com.xl.ruiji.service.CategoryService;
import com.xl.ruiji.service.DishService;
import com.xl.ruiji.service.SetMealDishService;
import com.xl.ruiji.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    DishService dishService;
    @Autowired
    SetMealService setMealService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    SetMealDishService setMealDishService;

    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize,String name){
        log.info("查询套餐");
        Page<Setmeal> page1 = new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        setMealService.page(page1,queryWrapper);

        Page<SetmealDto> setmealDtoPage = new Page<>();

        BeanUtils.copyProperties(page1,setmealDtoPage,"records");

        List<Setmeal> setmealList = page1.getRecords();

        List<SetmealDto> setmealDtoList = new ArrayList<>();
        for (Setmeal setmeal:setmealList) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal,setmealDto);
            String categoryId = setmeal.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String name1 = category.getName();
            setmealDto.setCategoryName(name1);
            setmealDtoList.add(setmealDto);
        }
        setmealDtoPage.setRecords(setmealDtoList);
        return R.success(setmealDtoPage);
    }
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("添加套餐");
        setMealService.saveSetmealDish(setmealDto);
       return R.success("添加成功");
    }
    @DeleteMapping
    @Transactional
    public R<String> delete(@RequestParam List<String> ids){
        log.info("删除套餐");
        setMealService.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        for (String id:ids) {
            queryWrapper.eq(SetmealDish::getSetmealId,id);
            setMealDishService.remove(queryWrapper);
        }
        return R.success("删除成功");
    }
    @PostMapping("/status/0")
    public R<String> isStatus0(@RequestParam("ids") List<String> ids){
        for (String id:ids) {
            Setmeal setmeal = new Setmeal();
            setmeal.setStatus(0);
            setmeal.setId(id);
            setMealService.updateById(setmeal);
        }
        return R.success("禁售成功");
    }
    @PostMapping("/status/1")
    public R<String> isStatus1(@RequestParam("ids") List<String> ids){
        for (String id:ids) {
            Setmeal setmeal = new Setmeal();
            setmeal.setStatus(1);
            setmeal.setId(id);
            setMealService.updateById(setmeal);
        }
        return R.success("起售成功");
    }
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable String id){
        log.info("套餐回显");
        SetmealDto setMealDto = setMealService.getSetMealDto(id);
        return R.success(setMealDto);
    }
    @PutMapping
    public R<String> put(@RequestBody SetmealDto setmealDto){
        log.info("修改套餐");
        setMealService.updateSetMealDto(setmealDto);
        return   R.success("修改成功");
    }
    @GetMapping("/list")
    public R<List<Setmeal>> list(String categoryId){
        log.info(categoryId+"------------");
        Setmeal setmeal= new Setmeal();
        setmeal.setCategoryId(categoryId);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId,categoryId);
        List<Setmeal> list = setMealService.list(queryWrapper);
        return R.success(list);
    }
    @GetMapping("/dish/{id}")
    public R<Dish> getDish(@PathVariable String id){

        Dish dish = dishService.getById(id);
        return R.success(dish);
    }

}
