package com.xl.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xl.ruiji.dto.OrdersDto;
import com.xl.ruiji.pojo.OrderDetail;
import com.xl.ruiji.pojo.Orders;
import com.xl.ruiji.pojo.R;
import com.xl.ruiji.pojo.User;
import com.xl.ruiji.service.OrderDetailService;
import com.xl.ruiji.service.OrdersService;
import com.xl.ruiji.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    OrdersService ordersService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    UserService userService;
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        ordersService.submit(orders);
        return R.success("下单成功");
    }
    @GetMapping("/userPage")
    @Transactional
    public R<Page> userPage(int page,int pageSize,HttpSession session){
        log.info("用户查看订单");
        //查询订单号
        Page<Orders> page1 = new Page<>(page,pageSize);
        String user = session.getAttribute("user").toString();
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId,user);
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(page1,queryWrapper);
        //查询订单明细
        Page<OrdersDto> ordersDtoPage = new Page<>();
        BeanUtils.copyProperties(page1,ordersDtoPage,"records");

        List<OrdersDto> orderDetails = new ArrayList<>();
        List<Orders> orders = page1.getRecords();

        for (Orders or:orders) {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(or,ordersDto);

            String orId = or.getId();
            LambdaQueryWrapper<OrderDetail> queryWrapper1= new LambdaQueryWrapper<>();
            queryWrapper1.eq(OrderDetail::getOrderId,orId);
            List<OrderDetail> list = orderDetailService.list(queryWrapper1);
            ordersDto.setOrderDetails(list);

            orderDetails.add(ordersDto);
        }
        ordersDtoPage.setRecords(orderDetails);

        return R.success(ordersDtoPage);
    }

    @GetMapping("/page")
    @Transactional
    public R<Page> page(int page, int pageSize, String number, String beginTime,String endTime){
        log.info("查看订单");
        Page<Orders> ordersPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(number!=null,Orders::getNumber,number);
        ordersLambdaQueryWrapper.ge(beginTime!=null,Orders::getOrderTime,beginTime);
        ordersLambdaQueryWrapper.le(endTime!=null,Orders::getOrderTime,endTime);
        ordersService.page(ordersPage,ordersLambdaQueryWrapper);

        Page<OrdersDto> ordersDtoPage = new Page<>();
        BeanUtils.copyProperties(ordersPage,ordersDtoPage,"records");

        List<Orders> records = ordersPage.getRecords();
        List<OrdersDto> ordersDtos = new ArrayList<>();

        for (Orders orders:records) {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(orders,ordersDto);

            String userId = orders.getUserId();
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getId,userId);
            User one = userService.getOne(queryWrapper);

            ordersDto.setUserName(one.getName());
            ordersDtos.add(ordersDto);
        }
        ordersDtoPage.setRecords(ordersDtos);
        return R.success(ordersDtoPage);
    }
    @PutMapping
    public R<String> put(@RequestBody Orders orders){
        log.info("更改订单状态");
        ordersService.updateById(orders);
        return R.success("派送成功");
    }
    @PostMapping("/again")
    public R<Orders> again(@RequestBody Orders orders){
        return R.success(orders);
    }

}
