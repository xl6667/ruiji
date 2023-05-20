package com.xl.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xl.ruiji.pojo.R;
import com.xl.ruiji.pojo.ShoppingCart;
import com.xl.ruiji.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(HttpSession session){
        String user = session.getAttribute("user").toString();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,user);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }
    @PostMapping("/add")
    public R<ShoppingCart> save(@RequestBody ShoppingCart shoppingCart, HttpSession session){
        log.info("添加购物车"+shoppingCart);
        String userID= session.getAttribute("user").toString();
        shoppingCart.setUserId(userID);
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userID);
        if (shoppingCart.getDishId()!=null){
            //添加菜品
            queryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());

        }else {
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        ShoppingCart serviceOne = shoppingCartService.getOne(queryWrapper);

        if(serviceOne!=null){
            //存在
            serviceOne.setNumber(serviceOne.getNumber()+1);
            shoppingCartService.updateById(serviceOne);
            shoppingCart = serviceOne;
        }else {
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
        }

        return R.success(shoppingCart);
    }
    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart,HttpSession session){
        String user = session.getAttribute("user").toString();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,user);
        queryWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        queryWrapper.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        if(one.getNumber()==1){
            shoppingCartService.removeById(one);
        }else {
            one.setNumber(one.getNumber()-1);
            shoppingCartService.updateById(one);
        }
        return R.success("删除成功");
    }
    @DeleteMapping("/clean")
    public R<String> clean(HttpSession session){
        log.info("清空购物车");
        String user = session.getAttribute("user").toString();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,user);
        shoppingCartService.remove(queryWrapper);
        return R.success("清除成功");
    }
}
