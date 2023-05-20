package com.xl.ruiji.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xl.ruiji.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmpMapper extends BaseMapper<Employee> {
}
