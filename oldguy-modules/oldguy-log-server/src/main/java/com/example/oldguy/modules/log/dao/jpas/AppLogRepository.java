package com.example.oldguy.modules.log.dao.jpas;

import com.example.oldguy.modules.log.dao.entities.AppLog;
import org.springframework.data.repository.CrudRepository;

/**
 * @ClassName: AppLogRepository
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/16 0016 下午 4:26
 **/
public interface AppLogRepository extends CrudRepository<AppLog, String> {
}
