package com.xl.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xl.ruiji.pojo.Employee;
import com.xl.ruiji.pojo.R;
import com.xl.ruiji.service.EmpService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;




@Slf4j
@RequestMapping("/employee")
@RestController
public class EmployeeController {
    @Autowired
    private EmpService empService;
    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping("/login")
    public R<Employee> empLogin(HttpServletRequest httpServletRequest, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = empService.getOne(queryWrapper);

        if (emp == null){
            return R.error("登录失败");
        }

        if (!password.equals(emp.getPassword())){
            log.info("密码有误");
            return R.error("密码有误");
        }

        if (emp.getStatus() == 0){
            return R.error("账号已禁用");
        }

        httpServletRequest.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    @PostMapping
    public R<String> save(@RequestBody Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        empService.save(employee);
        return R.success("添加成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Employee> page1 = new Page(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        empService.page(page1);
        return R.success(page1);
    }
    @PutMapping
    public R<String> updateSta(@RequestBody Employee employee){
        log.info("禁用管理");
        empService.updateById(employee);
        return R.success("禁用成功");
    }

    @GetMapping("/{id}")
    public R<Employee> updateEmp(@PathVariable String id){
        log.info("修改回显");
        Employee employee = empService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }
        return R.error("未查询到");
    }
}
