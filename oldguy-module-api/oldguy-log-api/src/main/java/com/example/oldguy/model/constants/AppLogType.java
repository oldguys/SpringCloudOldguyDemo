package com.example.oldguy.model.constants;

/**
 * @ClassName: AppLogType
 * @Author: ren
 * @Description: 日志类别
 * @CreateTIme: 2019/7/16 0016 下午 4:05
 **/
public enum AppLogType {

    /**
     *  CRUD
     */
    CRUD("101", "CRUD");

    /**
     *  编码
     */
    private String code;

    /**
     *  名称
     */
    private String value;

    AppLogType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


