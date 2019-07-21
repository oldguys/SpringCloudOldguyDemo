#### SpringCloud脚手架

> 架构描述: 
>
> 1. springcloud 版本：2.1.5.RELEASE 
> 2. nacos 版本：0.2.1.RELEASE
> 3. mybatis-plus: 3.0.5
> 
> 环境：
> 1. jdk: 1.8
> 2. mysql: 5.7
> 3. redis: 4.0.14
> 4. mongodb: 4.0.6
>

######  模块：

    -commom: micro services app 常用基础类
    -generator: 自动建表 + mybatis mapper.xml 文件创建
    -auth: 授权
        - default-auth-server: Token,权限认证
    -clients:
        - auth-client: 接入认证和授权的入口
        - log-client: 接入日志的入口
    -gate: 网关
    -module-api: 模型层，实体以及微服务调用service抽象
    -modules: micro services app
        - log-server: 日志服务器( webflux + mongodb)
        - multi-datasource: 多数据库 mybatis示例
        - base: 通用示例及基础业务 

###### 功能介绍：
    
1.  [auth-server 认证模块](https://github.com/oldguys/SpringCloudOldguyDemo/blob/master/docs/auth/Auth%20%E6%A8%A1%E5%9D%97%E4%BB%8B%E7%BB%8D.md)
2. [spring-cloud-gateway路由](https://github.com/oldguys/SpringCloudOldguyDemo/blob/master/docs/auth/GateWay%20%E6%8B%A6%E6%88%AA.md)
3. [log-server 日志服务](https://github.com/oldguys/SpringCloudOldguyDemo/blob/master/docs/auth/log-server%20%E6%97%A5%E5%BF%97%E6%9C%8D%E5%8A%A1.md)
4. [Mybatis代码生成工具](https://github.com/oldguys/SpringCloudOldguyDemo/blob/master/docs/common/%E8%87%AA%E5%AE%9A%E4%B9%89%20mybatis%E4%BB%A3%E7%A0%81%E7%94%9F%E6%88%90%E5%99%A8.md)


###### 核心功能设计：
1. [auth-client 认证与授权 核心逻辑](https://github.com/oldguys/SpringCloudOldguyDemo/blob/master/docs/auth/%E5%8A%9F%E8%83%BD%E4%BB%A3%E7%A0%81%E4%BB%8B%E7%BB%8D.md)
2. [基于 mybatis-plus-boot-starter 改造的多数据源](https://github.com/oldguys/SpringCloudOldguyDemo/blob/master/docs/modules/Mybatis-plus%20%E9%85%8D%E7%BD%AE%E5%A4%9A%E6%95%B0%E6%8D%AE%E6%BA%90.md)
3. [mybatis代码生成器核心逻辑](https://www.jianshu.com/p/7115f58dc1fb)
4. 


