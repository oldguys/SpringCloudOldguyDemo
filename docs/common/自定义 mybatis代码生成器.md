#### mybatis代码生成器

**模块位置：oldguy-generator**

1. 引入Maven
```
<dependency>
    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-generator</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

2. 配置环境变量

**type-aliases-package:** 需要扫描的实体位置
**template-location:** mybatis mapper 模板
**application-location:** 项目位置
**mapper-generate-config-location:** resources/mappers下xml映射位置

```
mybatis:
  type-aliases-package: com.example.oldguy.model.dao
  template-location: configs/mapper/mybatis-template.xml
  application-location: D:/workspace/demo/SpringCloudOldguyDemo/oldguy-auth/oldguy-default-auth-server
  mapper-generate-config-location: configs/mapper/mybatis-mapper-generate-config.perporties

```
 
3. 使用 @Entity 加入被自动建表实体

```
package com.example.oldguy.model.workflow.entities;

import com.example.oldguy.model.annotation.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @ClassName: UserEntity
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/28 0028 上午 9:16
 **/
@Data
@Entity(pre = "sys_")
public class UserEntity extends CommonBaseEntity {

    /**
     *  用户ID
     */
    private String userId;

    /**
     *  用户名
     */
    private String username;

    /**
     *  加盐
     */
    @JsonIgnore
    private String salt;

    /**
     *  密码
     */
    @JsonIgnore
    private String password;
}

```

4. 使用注解 @EnableMyBatisGenerator 开启

```
package com.example.oldguy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableMyBatisGenerator
@EnableAuthClient
@MapperScan(basePackages = "com.example.oldguy.modules.auth.dao.jpas")
@EnableTransactionManagement
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.example")
public class OldguyDefaultAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldguyDefaultAuthServerApplication.class, args);
    }

}

```

###### 以上完成了自动建表，自动建mapper构建
---

类型匹配问题
com.example.oldguy.commons.services.impls.MySQLTableFactory

```
package com.example.oldguy.commons.services.impls;/**
 * Created by Administrator on 2018/10/23 0023.
 */


import com.example.oldguy.commons.dto.db.SqlTableObject;
import com.example.oldguy.commons.services.TableFactory;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/23 0023 13:54
 */
public class MySQLTableFactory implements TableFactory {

    private static final Map<Class, String> columnType;

    static {
        columnType = new HashMap<>();
        columnType.put(Integer.class, "INT");
        columnType.put(Long.class, "BIGINT");
        columnType.put(String.class, "VARCHAR");
        columnType.put(Date.class, "DATETIME");
        columnType.put(Boolean.class, "TINYINT");
        columnType.put(Double.class, "DOUBLE");
    }

    ....... // 省略代码
}

```

基于Thymeleaf 的xml 处理工具类：

```
package com.example.oldguy.commons.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * @author ren
 * @date 2019/2/1
 */
public class ThymeleafUtils {

    private static ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    private static TemplateEngine templateEngine = new TemplateEngine();

    static {
        templateResolver.setSuffix(".xml");
        templateResolver.setTemplateMode(TemplateMode.XML);
        templateEngine.setTemplateResolver(templateResolver);
    }

    /**
     *  处理XML 文件
     * @param classPathTemplate 类目录
     * @param context 注入变量
     * @return
     */
    public static String processXML(String classPathTemplate, Context context) {
        return templateEngine.process(classPathTemplate, context);
    }

}

```

