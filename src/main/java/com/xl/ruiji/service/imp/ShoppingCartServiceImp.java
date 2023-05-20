package com.xl.ruiji.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xl.ruiji.mapper.ShoppingCartMapper;
import com.xl.ruiji.pojo.ShoppingCart;
import com.xl.ruiji.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImp extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
