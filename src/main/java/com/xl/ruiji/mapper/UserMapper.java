package com.xl.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xl.ruiji.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
