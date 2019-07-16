package com.example.oldguy.exceptions;

import lombok.Data;

/**
 * @ClassName: TokenException
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/14 0014 下午 4:33
 **/
@Data
public class TokenException extends RuntimeException {

    private int code;
    public TokenException(int code, String message) {
        super(message);
        this.code = code;
    }
}
