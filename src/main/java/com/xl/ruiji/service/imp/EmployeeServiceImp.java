package com.xl.ruiji.service.imp;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xl.ruiji.mapper.EmpMapper;
import com.xl.ruiji.pojo.Employee;
import com.xl.ruiji.service.EmpService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImp extends ServiceImpl<EmpMapper,Employee> implements EmpService{

}
