package com.xl.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xl.ruiji.dto.DishDto;
import com.xl.ruiji.pojo.Dish;
import com.xl.ruiji.pojo.R;


public interface DishService extends IService<Dish> {
    //添加菜品
    public void dishWithFlavor(DishDto dishDto);
    //根据ID回显菜品数据
    public R<DishDto> getDishWithFlavor(String id);
    //修改菜品
    public void updateDishWithFlavor(DishDto dishDto);
}
