package com.xl.ruiji.until;

import com.xl.ruiji.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/*
*全局异常处理
* */
@ControllerAdvice(annotations = {RestController.class})
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.info(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            String[] sqlit = ex.getMessage().split(" ");
            String msg = sqlit[2]+"已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }


    @ExceptionHandler(CustomException.class)
    public R<String> customException(CustomException ex){
        log.info(ex.getMessage());

        return R.error(ex.getMessage());
    }
}
