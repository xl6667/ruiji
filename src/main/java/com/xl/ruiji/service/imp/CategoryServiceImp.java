package com.xl.ruiji.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xl.ruiji.mapper.CategoryMapper;
import com.xl.ruiji.pojo.Category;
import com.xl.ruiji.pojo.Dish;
import com.xl.ruiji.pojo.Setmeal;
import com.xl.ruiji.service.CategoryService;
import com.xl.ruiji.service.DishService;
import com.xl.ruiji.service.SetMealService;
import com.xl.ruiji.until.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImp extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    DishService dishService;
    @Autowired
    SetMealService setMealService;
    @Override
    public void move(String id) {

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,id);

        LambdaQueryWrapper<Setmeal> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Setmeal::getCategoryId,id);

        int count = (int) (dishService.count() + setMealService.count());

        if (count > 0){
            throw new CustomException("已经关联套餐");
        }
        super.removeById(id);

    }
}
