package com.example.oldguy.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AppLogAddForm
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 2:54
 **/
@Data
public class AppLogAddForm implements Serializable {

    /**
     *  日志类型
     */
    private String type;

    /**
     *  日志内容
     */
    private String message;

    /**
     *  数据
     */
    private String data;

    public AppLogAddForm() {
    }

    public AppLogAddForm(String type, String message, String data) {
        this.type = type;
        this.message = message;
        this.data = data;
    }

}
