package com.example.oldguy.handles;

import com.example.oldguy.exceptions.PermException;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.utils.Log4jUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: PermsExceptionHandle
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 9:40
 **/
@RestControllerAdvice
public class PermExceptionHandler {

    public PermExceptionHandler(){
        Log4jUtils.getInstance(PermExceptionHandler.class).debug("开启-PermExceptionHandler");
    }

    @ExceptionHandler(PermException.class)
    public RspMsg permException(PermException e) {
        Log4jUtils.getInstance(getClass()).debug("权限异常：" + e.getMessage());
        return new RspMsg().code(e.getCode()).message(e.getMessage());
    }
}
