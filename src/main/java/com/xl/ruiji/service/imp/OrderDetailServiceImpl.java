package com.xl.ruiji.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xl.ruiji.mapper.OrderDetailMapper;
import com.xl.ruiji.pojo.OrderDetail;
import com.xl.ruiji.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}