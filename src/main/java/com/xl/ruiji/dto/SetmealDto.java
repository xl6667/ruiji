package com.xl.ruiji.dto;


import com.xl.ruiji.pojo.Setmeal;
import com.xl.ruiji.pojo.SetmealDish;
import lombok.Data;

import java.util.List;


@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
