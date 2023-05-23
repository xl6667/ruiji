package com.xl.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xl.ruiji.dto.DishDto;
import com.xl.ruiji.pojo.Dish;


public interface DishService extends IService<Dish> {
    //添加菜品
    void dishWithFlavor(DishDto dishDto);
    //根据ID回显菜品数据
    DishDto getDishWithFlavor(String id);
    //修改菜品
    void updateDishWithFlavor(DishDto dishDto);
}
