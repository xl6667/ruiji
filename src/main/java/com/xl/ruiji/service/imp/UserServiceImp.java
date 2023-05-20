package com.xl.ruiji.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xl.ruiji.mapper.UserMapper;
import com.xl.ruiji.pojo.User;
import com.xl.ruiji.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
}
