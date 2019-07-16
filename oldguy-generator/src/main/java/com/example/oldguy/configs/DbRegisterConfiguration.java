package com.example.oldguy.configs;

import com.example.oldguy.commons.dto.db.DbRegisterProperties;
import com.example.oldguy.commons.services.DbRegister;
import com.example.oldguy.commons.services.impls.MySQLTableFactory;
import com.example.oldguy.utils.Log4jUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/15 0015 17:45
 */
public class DbRegisterConfiguration {

    @Value("${mybatis.application-location}")
    private String applicationLocation;
    @Value("${mybatis.template-location}")
    private String templateClassPath;

    /**
     * 初始化数据库
     */
    public void initDB(JdbcTemplate jdbcTemplate, String typeAliasesPackage) {

        Log4jUtils.getInstance(getClass()).debug("全局文件位置:" + applicationLocation);
        Log4jUtils.getInstance(getClass()).debug("mybatis模板位置:" + templateClassPath);

        DbRegisterProperties properties = new DbRegisterProperties();
        properties.setPackageNames(splitPackagesPath(typeAliasesPackage));
        properties.setFactory(new MySQLTableFactory());
        properties.setApplicationLocation(applicationLocation);
        properties.setJdbcTemplate(jdbcTemplate);
        properties.setTemplateClassPath(templateClassPath);

        DbRegister dbRegister = new DbRegister();

        // 注册
        dbRegister.register(properties);
    }

    private List<String> splitPackagesPath(String typeAliasesPackage) {
        List<String> paths = new ArrayList<>();
        String[] packagePaths = typeAliasesPackage.split(";");
        for (String path : packagePaths) {
            if (!StringUtils.isEmpty(path)) {
                paths.add(path);
            }
        }
        return paths;
    }


}
