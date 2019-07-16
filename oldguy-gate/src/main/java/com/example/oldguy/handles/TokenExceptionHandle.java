package com.example.oldguy.handles;

import com.example.oldguy.exceptions.TokenException;
import com.example.oldguy.model.dto.RspMsg;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: TokenExceptionHandle
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/14 0014 下午 4:32
 **/
@RestControllerAdvice
public class TokenExceptionHandle {

    @ExceptionHandler(TokenException.class)
    public RspMsg TokenException(TokenException e){
        return new RspMsg().code(e.getCode()).message(e.getMessage());
    }
}
