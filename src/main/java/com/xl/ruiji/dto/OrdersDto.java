package com.xl.ruiji.dto;



import com.xl.ruiji.pojo.OrderDetail;
import com.xl.ruiji.pojo.Orders;
import lombok.Data;

import java.util.List;


@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
