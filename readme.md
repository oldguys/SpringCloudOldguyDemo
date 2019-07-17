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
    
    1. 