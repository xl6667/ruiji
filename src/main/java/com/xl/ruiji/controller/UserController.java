package com.xl.ruiji.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xl.ruiji.pojo.R;
import com.xl.ruiji.pojo.User;
import com.xl.ruiji.service.UserService;
import com.xl.ruiji.until.ChineseNameUntil;
import com.xl.ruiji.until.SMSUtils;
import com.xl.ruiji.until.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        log.info(code+"------");
        //SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);
        session.setAttribute(phone,code);
        return R.success("短信发送成功");
    }
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");
        Object attribute = session.getAttribute(phone);
        if(attribute!=null && code.equals(attribute)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if(user==null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                user.setName(ChineseNameUntil.getName());
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
       return R.error("登录失败");
    }

    @PostMapping("loginout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }
}
