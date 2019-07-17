package com.example.oldguy.model.constans;

import java.io.Serializable;

/**
 * @ClassName: RspMsgConstants
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/9 0009 下午 8:31
 **/
public interface RspMsgConstants {

    String DEFAULT_SUCCESS_MESSAGE = "操作成功!";

    String DEFAULT_ERROR_MESSAGE = "操作失败!";

    static String getNotFoundMessage(Serializable id){
        return "没有找到 [ id = "+id+" ] 相关数据 !";
    }
}
