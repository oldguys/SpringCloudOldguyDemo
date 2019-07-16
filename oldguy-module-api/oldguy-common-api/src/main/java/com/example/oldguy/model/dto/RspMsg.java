package com.example.oldguy.model.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oldguy.model.constans.HttpStatus;
import lombok.Data;

import static com.example.oldguy.model.constans.RspMsgConstants.DEFAULT_ERROR_MESSAGE;
import static com.example.oldguy.model.constans.RspMsgConstants.DEFAULT_SUCCESS_MESSAGE;

/**
 * @ClassName: RspMsg
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/9 0009 下午 8:22
 **/
@Data
public class RspMsg<T> {

    private Integer code;

    private String message;

    private T data;

    private Page<T> page;

    /**
     *  默认成功接口
     * @return
     */
    public RspMsg<T> defaultSuccessMessage() {
        code = HttpStatus.OK.value();
        message = DEFAULT_SUCCESS_MESSAGE;
        return this;
    }

    /**
     *  默认错误接口
     * @return
     */
    public RspMsg<T> defaultErrorMessage() {
        code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        message = DEFAULT_ERROR_MESSAGE;
        return this;
    }

    public RspMsg<T> code(Integer code){
        this.code = code;
        return this;
    }

    public RspMsg<T> message(String message) {
        this.message = message;
        return this;
    }

    public RspMsg<T> data(T data) {
        this.data = data;
        return this;
    }

    public RspMsg<T> page(Page<T> page) {
        this.page = page;
        return this;
    }
}
