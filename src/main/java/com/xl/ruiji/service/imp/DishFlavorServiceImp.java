package com.xl.ruiji.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xl.ruiji.mapper.DishFlavorMapper;
import com.xl.ruiji.pojo.DishFlavor;
import com.xl.ruiji.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImp extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
