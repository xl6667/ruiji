package com.xl.ruiji.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xl.ruiji.dto.DishDto;
import com.xl.ruiji.mapper.DishMapper;
import com.xl.ruiji.pojo.Dish;
import com.xl.ruiji.pojo.DishFlavor;
import com.xl.ruiji.service.DishFlavorService;
import com.xl.ruiji.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImp extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void dishWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        String id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor dish:flavors) {
            dish.setDishId(id);
        }
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getDishWithFlavor(String id) {
        Dish dish = this.getById(id);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(list);


        return dishDto;
    }

    @Override
    @Transactional
    public void updateDishWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        String id = dishDto.getId();
        for (DishFlavor dishFlavor:dishDto.getFlavors()) {
            dishFlavor.setDishId(id);
        }
        dishFlavorService.updateBatchById(dishDto.getFlavors());
    }

}
