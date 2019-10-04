package com.example.oldguy.services.wokflows;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: HisUserTaskApi
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/8/21 0021 上午 9:48
 **/
@FeignClient("HisUserTask")
public interface HisUserTaskApi {


}
