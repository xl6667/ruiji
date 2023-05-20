package com.xl.ruiji.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xl.ruiji.dto.SetmealDto;
import com.xl.ruiji.mapper.SetMealMapper;
import com.xl.ruiji.pojo.Setmeal;
import com.xl.ruiji.pojo.SetmealDish;
import com.xl.ruiji.service.SetMealDishService;
import com.xl.ruiji.service.SetMealService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetMealServiceImp extends ServiceImpl<SetMealMapper,Setmeal> implements SetMealService {
    @Autowired
    SetMealDishService setMealDishService;
    @Override
    @Transactional
    public void saveSetmealDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish:setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setMealDishService.saveBatch(setmealDishes);
    }

    @Override
    public SetmealDto getSetMealDto(String id) {
        SetmealDto setmealDto = new SetmealDto();
        Setmeal setmeal = this.getById(id);
        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setMealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    @Override
    public void updateSetMealDto(SetmealDto setmealDto) {
        this.updateById(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish:setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setMealDishService.updateBatchById(setmealDishes);
    }
}
