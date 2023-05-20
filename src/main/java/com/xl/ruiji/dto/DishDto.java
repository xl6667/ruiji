package com.xl.ruiji.dto;



import com.xl.ruiji.pojo.Dish;
import com.xl.ruiji.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;


@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
