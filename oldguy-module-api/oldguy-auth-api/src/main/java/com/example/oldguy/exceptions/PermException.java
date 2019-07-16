package com.example.oldguy.exceptions;

import lombok.Data;

/**
 * @ClassName: PermException
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 9:41
 **/
@Data
public class PermException extends RuntimeException {

    private int code;

    public PermException(String message, int code) {
        super(message);
        this.code = code;
    }
}
