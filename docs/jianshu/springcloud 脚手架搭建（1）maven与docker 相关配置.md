### springcloud+docker 脚手架搭建（1）

> 微服务项目主要需要分为 模型层，业务层，网关层。而springcloud+maven构建项目时，往往需要 抽象父级maven项目和子maven项目。本章主要描述maven父子项目构建时候需要的注意点
>  github [https://github.com/oldguys/SpringCloudOldguyDemo](https://github.com/oldguys/SpringCloudOldguyDemo)
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

**step1**：最顶层模块 spring-cloud-oldguy 所有项目的通用模块，在此层主要配置项目通用的内容
```
    <?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.5.RELEASE</version>
    </parent>
    <groupId>com.example.oldguy</groupId>
    <artifactId>spring-cloud-oldguy</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-cloud-oldguy</name>

    <packaging>pom</packaging>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>2.1.5.RELEASE</spring-cloud.version>
        <nacos.version>0.2.1.RELEASE</nacos.version>
        <swagger.version>2.7.0</swagger.version>
        <swagger-bootstrap-ui.version>1.9.4</swagger-bootstrap-ui.version>
        <mybatis-plus.version>3.0.5</mybatis-plus.version>
        <mysql-driver.version>5.1.47</mysql-driver.version>
        <druid.version>1.1.12</druid.version>
        <skipTests>true</skipTests>
    </properties>

    <modules>
        <module>oldguy-module-api</module>
        <module>oldguy-clients</module>
        <module>oldguy-generator</module>
        <module>oldguy-common</module>
        <module>oldguy-modules</module>
        <module>oldguy-auth</module>
        <module>oldguy-gate</module>
    </modules>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <!--<scope>import</scope>-->
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>


        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${swagger.version}</version>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${swagger.version}</version>
        </dependency>

        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>swagger-bootstrap-ui</artifactId>
            <version>${swagger-bootstrap-ui.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
            <version>${mybatis-plus.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>

        <dependency>
            <groupId>javax.persistence</groupId>
            <artifactId>persistence-api</artifactId>
            <version>1.0</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.47</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

```
**其中需要注意点**
1. springcloud项目 默认构建都会配置插件
```
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
```
此插件在模型层构建的时候，由于模型层只是提供通用的实体，接口，常量等，不具备SpringBoot启动条件，此时编译会报错。需要将此模块替换 为
```
        <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
```
此时只会走默认maven项目编译。

2. 需要引入子项目，如果不引入，则在 mvn package的时候，不会编译。

```
    <modules>
        <module>oldguy-module-api</module>
        <module>oldguy-clients</module>
        <module>oldguy-generator</module>
        <module>oldguy-common</module>
        <module>oldguy-modules</module>
        <module>oldguy-auth</module>
        <module>oldguy-gate</module>
    </modules>
```
3. 全局环境变量，配置完成之后，子maven项目引用的时候可以直接调用。
```
    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>2.1.5.RELEASE</spring-cloud.version>
        <nacos.version>0.2.1.RELEASE</nacos.version>
        <swagger.version>2.7.0</swagger.version>
        <swagger-bootstrap-ui.version>1.9.4</swagger-bootstrap-ui.version>
        <mybatis-plus.version>3.0.5</mybatis-plus.version>
        <mysql-driver.version>5.1.47</mysql-driver.version>
        <druid.version>1.1.12</druid.version>
        <skipTests>true</skipTests>
    </properties>
```
4. 配置打包类型，默认为jar 但是抽象层不需要打包，则需要修改为pom
```
    <packaging>pom</packaging>
```
---

**step2**：模型层 oldguy-module-api 构建 
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>spring-cloud-oldguy</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <packaging>pom</packaging>

    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-module-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>oldguy-module-api</name>

    <modules>
        <module>oldguy-workflow-api</module>
        <module>oldguy-base-api</module>
        <module>oldguy-log-api</module>
        <module>oldguy-common-api</module>
        <module>oldguy-auth-api</module>
    </modules>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
        </dependency>
    </dependencies>

</project>

```
1. 继承父maven项目
```
    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>spring-cloud-oldguy</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
```

**注意：其中被 省略的 <build></build> <dependency></dependency>会默认继承父级的**

---

**step3**：配置抽象模型层  oldguy-common-api
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>oldguy-module-api</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-common-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>oldguy-common-api</name>
    <description>Demo project for Spring Boot</description>

</project>
```
**注意，此处不能使用<packaging>pom</packaging>，此层需要提供公共抽象类给其他模块，需要编译层 jar**

---

**step4:** 权限认证模型 oldguy-auth-api
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>oldguy-module-api</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-auth-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>oldguy-auth-api</name>

    <description>Demo project for Spring Boot</description>

    <dependencies>
        <dependency>
            <groupId>com.example.oldguy</groupId>
            <artifactId>oldguy-common-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
</project>
 ```

1. 此处调用前面的通用模型层,引用整套系统的通用基类
 ```
        <dependency>
            <groupId>com.example.oldguy</groupId>
            <artifactId>oldguy-common-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

 ```

--- 

###### 以上完成了模型层的构建，接下来是clients层

**step5**：clients层，clients层主要用于做集成功能，如类似于SpringBoot的@Enable* 此类型的注解，抽象到此模块下，

 ```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>spring-cloud-oldguy</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <packaging>pom</packaging>

    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-clients</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>oldguy-clients</name>
    <description>Demo project for Spring Boot</description>

    <modules>
        <module>oldguy-mybatis-plus-client</module>
        <module>oldguy-nacos-client</module>
        <module>oldguy-auth-client</module>
        <module>oldguy-log-client</module>
    </modules>

</project>

 ```

---

**step6**：auth-client 授权认证client，用于各个微服务app集成 认证中心，配置通用拦截器
 ```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>oldguy-clients</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-auth-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>oldguy-auth-client</name>
    <description>Demo project for Spring Boot</description>

    <dependencies>
        <dependency>
            <groupId>com.example.oldguy</groupId>
            <artifactId>oldguy-auth-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.8.RELEASE</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-core</artifactId>
        </dependency>
    </dependencies>

</project>
 ```
**此处开始由其他层引用模型层 oldguy-auth-api**

---

**step6**：微服务App 通用层 ，此处主要为app的各种通用工具类
 
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>spring-cloud-oldguy</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-common</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>oldguy-common</name>
    <description>Demo project for Spring Boot</description>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>com.example.oldguy</groupId>
            <artifactId>oldguy-common-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
        </dependency>
        <dependency>
            <groupId>com.example.oldguy</groupId>
            <artifactId>oldguy-auth-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>

</project>

 ```
###### 以上完成了通用集成模块介绍和抽象工具模块介绍，下面为示例app 配置

---

**step7**：认证中心构建

1. 抽象认证服务层
 ```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>spring-cloud-oldguy</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <packaging>pom</packaging>

    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-auth</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>oldguy-auth</name>
    <description>Demo project for Spring Boot</description>

    <modules>
        <module>oldguy-default-auth-server</module>
    </modules>

</project>

 ```
2. 认证服务器

 ```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>oldguy-auth</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-default-auth-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>oldguy-default-auth-server</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <shiro.version>1.4.0</shiro.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>${nacos.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>3.4.0</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>${mybatis-plus.version}</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.10</version>
        </dependency>

        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-core</artifactId>
            <version>${shiro.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>${shiro.version}</version>
        </dependency>

        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
        </dependency>

        <!-- 本地 -->
        <dependency>
            <groupId>com.example.oldguy</groupId>
            <artifactId>oldguy-common</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>com.example.oldguy</groupId>
            <artifactId>oldguy-auth-client</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>com.example.oldguy</groupId>
            <artifactId>oldguy-generator</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>

            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>

            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.4.11</version>
                <configuration>
                    <imageName>${project.artifactId}</imageName>
                    <dockerDirectory>src/main/docker</dockerDirectory>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

 ```

**1) 配置springboot插件** 
此处需要配置springboot插件，因为生成的jar为springboot项目，不配置会抛找不到app.js 异常。
 ```
<plugin>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
 ```
**2) 配置docker插件**
  ```
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.4.11</version>
                <configuration>
                    <imageName>${project.artifactId}</imageName>
                    <dockerDirectory>src/main/docker</dockerDirectory>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
 ```
配置 Dockefile
 ```
#运行此项目还需要基于java镜像
FROM java:8
#将本地文件夹挂载到当前容器
VOLUME /tmp
#拷贝文件到容器，注意这里的jar包是事先准备好的一个演示Cloud的jar项目，需要放在Dockerfile同样的目录下
ADD oldguy-default-auth-server-0.0.1-SNAPSHOT.jar app.jar
RUN ["/bin/bash","-c","touch /app.jar"]
#指定JAVA 环境变量
ENV JAVA_HOME /jdk/jre
ENV PATH $PATH:$JAVA_HOME/bin
ENV CLASSPATH .:$JAVA_HOME/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
#开放8761端口
EXPOSE 9010
#配置容器启动后执行的命令
ENTRYPOINT ["java","-jar","/app.jar"]
 ```

配置 docker-compose.yml

 ```
version: '3'
services:
  oldguy-gate:
    image: oldguy-gate:latest
    restart: always
    volumes:
      - /etc/localtime:/etc/localtime
      - /home/dc-ui/volume/data:/data
    ports:
      - 9010:9010
    environment:
      - PROFILES=router,sit
      - NACOS_ADDR=192.168.62.129:8848
      - LOG_LEVEL=info           
 ```



**以上完成springcloud maven构建**

测试效果

step1: 上传项目到centos  /usr/workspace/SpringCloudOldguyDemo

step2: mvn clean package

![mvn clean package](https://upload-images.jianshu.io/upload_images/14387783-a5937f27aa1f62e1.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

step3 : cd /usr/workspace/SpringCloudOldguyDemo/oldguy-gate
mvn docker:build

![mvn docker:build ](https://upload-images.jianshu.io/upload_images/14387783-5b68e934fb6bc6a9.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

查看镜像是否存在 
![docker images](https://upload-images.jianshu.io/upload_images/14387783-a0760ae3b066f0d1.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


使用docker compose 启动
docker-compose up
![docker-compose up](https://upload-images.jianshu.io/upload_images/14387783-32b3df262b63c951.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

