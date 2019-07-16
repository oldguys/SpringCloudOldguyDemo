package com.example.oldguy.modules.handles;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.oldguy.model.constans.HttpStatus;
import com.example.oldguy.model.dto.RspMsg;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: TokenExceptionHandle
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/14 0014 下午 4:26
 **/
@RestControllerAdvice
public class TokenExceptionHandle {

    @ExceptionHandler(TokenExpiredException.class)
    public RspMsg TokenExpiredException(TokenExpiredException e) {
        return new RspMsg().code(HttpStatus.BAD_GATEWAY.value()).message(e.getMessage());
    }
}
