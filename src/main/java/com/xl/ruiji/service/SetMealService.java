package com.xl.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xl.ruiji.dto.SetmealDto;
import com.xl.ruiji.pojo.Setmeal;

public interface SetMealService extends IService<Setmeal> {
    public void saveSetmealDish(SetmealDto setmealDto);
    public SetmealDto getSetMealDto(String id);

    void updateSetMealDto(SetmealDto setmealDto);
}
