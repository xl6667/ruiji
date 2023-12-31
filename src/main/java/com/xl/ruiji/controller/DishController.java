package com.xl.ruiji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xl.ruiji.dto.DishDto;

import com.xl.ruiji.pojo.Category;
import com.xl.ruiji.pojo.Dish;

import com.xl.ruiji.pojo.DishFlavor;
import com.xl.ruiji.pojo.R;
import com.xl.ruiji.service.CategoryService;
import com.xl.ruiji.service.DishFlavorService;
import com.xl.ruiji.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    DishService dishService;
    @Autowired
    DishFlavorService dishFlavorService;
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){

        Page<Dish> page1 = new Page<>(page,pageSize);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Dish::getName,name);
        dishService.page(page1,queryWrapper);

        Page<DishDto> dishDtoPage = new Page<>();

        BeanUtils.copyProperties(page1,dishDtoPage,"records");

        List<Dish> records = page1.getRecords();

        List<DishDto> list = new ArrayList<>();

        for (Dish dish:records) {

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish,dishDto);

            String categoryId = dish.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                String name2 = category.getName();
                dishDto.setCategoryName(name2);
            }
            list.add(dishDto);
        }
        dishDtoPage.setRecords(list);


        return R.success(dishDtoPage);
    }
    @PostMapping
    public R<String> save(@RequestBody DishDto dto){
        log.info("添加菜品" + dto.toString());
        dishService.dishWithFlavor(dto);
        return R.success("添加成功");
    }
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable String id){
        log.info("修改菜品");
        R<DishDto> dishWithFlavor = dishService.getDishWithFlavor(id);
        return dishWithFlavor;
    }
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info("修改菜品");
        dishService.updateDishWithFlavor(dishDto);
        return R.success("修改成功");
    }
    @PostMapping("/status/0")
    public R<String> isStatus0(@RequestParam("ids") List<String> ids){
        log.info("禁售菜品");
        for (String id:ids) {
            Dish dish = new Dish();
            dish.setStatus(0);
            dish.setId(id);
            dishService.updateById(dish);
        }
        return R.success("禁售成功");
    }
    @PostMapping("/status/1")
    public R<String> isStatus1( @RequestParam("ids") List<String> ids){
        for (String id:ids) {
            Dish dish = new Dish();
            dish.setStatus(1);
            dish.setId(id);
            dishService.updateById(dish);
        }
        return R.success("起售成功");
    }
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<String> ids){
        for (String id:ids) {
            dishService.removeById(id);
        }
        return R.success("删除成功");
    }
    /*@GetMapping("/list")
    public R<List<Dish>> getDish(String categoryId,String name){
        Dish dish =new Dish();
        dish.setCategoryId(categoryId);
        dish.setName(name);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(dish.getName()!=null,Dish::getName,dish.getName());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }*/

    @GetMapping("/list")
    @Transactional
    public R<List<DishDto>> getDish(String categoryId,String name){

        Dish dish =new Dish();
        dish.setCategoryId(categoryId);
        dish.setName(name);

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(dish.getName()!=null,Dish::getName,dish.getName());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtos = new ArrayList<>();

        for (Dish dish1:list) {
            DishDto dishDto = new DishDto();


            if (categoryId!=null){
               dishDto.setName(categoryService.getById(categoryId).getName());
            }
            BeanUtils.copyProperties(dish1,dishDto);
            LambdaQueryWrapper<DishFlavor> queryWrapper1 =  new LambdaQueryWrapper<>();
            queryWrapper1.eq(DishFlavor::getDishId,dish1.getId());
            List<DishFlavor> flavors = dishFlavorService.list(queryWrapper1);

            dishDto.setFlavors(flavors);

            dishDtos.add(dishDto);
        }


        return R.success(dishDtos);
    }

}
