package com.xl.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xl.ruiji.pojo.Orders;

public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
