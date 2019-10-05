> 微服务是非常常用的，本章主要描述的配置基于阿里nacos配置中心的基本使用
>   github [https://github.com/oldguys/SpringCloudOldguyDemo](https://github.com/oldguys/SpringCloudOldguyDemo) 测试项目为：oldguy-base
> 测试版本为：nacos 0.2.1.RELEASE

1.引入依赖
```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            <version>${nacos.version}</version>
        </dependency>
```
PS: 在springcloud的环境下 引入 @EnableDiscoveryClient，只要引入上面的maven依赖，自然就可以开启nacos配置中心。

2. 必须创建 bootstrap.yml文件 
```
spring:
  cloud:
    nacos:
      config:
        server-addr: localhost:8848 # 配置配置中心 （必选，为开启配置中心的开关）
        file-extension: yml #默认为.properties 尾缀的文件,需要通过此处开启读取.yml 配置文件（可选，如果是使用默认尾缀请无视）
```

3.配置中心填写 XXX.yml(properties)
![image.png](https://upload-images.jianshu.io/upload_images/14387783-a95426c9c02e4c2d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**默认的，配置命名规范为：spring.appliaction.name+“-”+profiles.active+.xml(.properties)**
```
#  port: 0
spring:
  application:
    name: oldguy-base
  profiles:
    active: test,a
```
则有效的为：oldguy-base-test.yml ; oldguy-base-a.yml


以上就完成了默认配置，可以体验下效果。此处省略贴图。

**注意：**
1. 其中 **spring.appliaction.name** 为spring本身的配置文件，**bootstrap.yml** 文件是无效的。
2. **profiles.active**没有配置，则为省略为：**spring.appliaction.name+.xml(.properties)**

------
### 扩展配置
1. 可以在 **bootstrap.yml** 文件中修改默认配置名称  使用spring.cloud.config.name，如：微服务名称为oldguy-base，此处修改为 example。同样的，在配置文件（application.yml）中 添加profiles.active也可以触发监听example-（profile.actives）.yml 如：example-a.yml 

2. 可以在 **bootstrap.yml** 文件中配置其他配置文件，如：以上把配置默认监听改为example，可以再 spring.cloud.config.shared-dataids 配置其他监听的文件名称。但是默认是不开启 动态更新的，需要在 spring.cloud.config.refreshable-dataids:进行额外的配置（在shared-dataids的配置集合中选择）

3. 如果出现同名的变量，默认根据配置顺序进行覆盖，如果是 shared-dataids 中的，先按照配置顺序覆盖，如果出现在 spring.appliaction.name+.xml(.properties) 中的会覆盖掉 shared-dataids

```
spring:
  cloud:
    nacos:
      config:
        server-addr: localhost:8848 # 配置配置中心
        file-extension: yml #默认为.properties 尾缀的文件,需要通过此处开启读取.yml 配置文件
        active-profiles: a # 测试效果为无效，从源码中默认引用的依然是 配置文件（非bootstrap.yml）中的 spring.application.name
        name: example # 此处可以修改默认读取的配置文件名，替换掉 spring.application.name (另外：bootstrap.yml配置的spring.application.name对于配置中心无效)
        shared-dataids: oldguy-base-c.yml,oldguy-base-b.yml #配置非默认配置变量名的配置文件,此处默认是不开启动态更新的。
        refreshable-dataids: oldguy-base-a.yml # 激活 在 shared-dataids 中配置的其他配置文件的 动态更新
```
