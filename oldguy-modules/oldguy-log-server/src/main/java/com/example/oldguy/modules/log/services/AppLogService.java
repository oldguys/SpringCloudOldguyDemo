package com.example.oldguy.modules.log.services;

import com.example.oldguy.modules.log.dao.jpas.AppLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: AppLogService
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/16 0016 下午 4:30
 **/
@Service
public class AppLogService {

    @Autowired
    private AppLogRepository appLogRepository;

}
