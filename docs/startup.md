###### Nacos 
https://hub.docker.com/r/nacos/nacos-server

安装位置 /usr/docker-temp/nacos-docker

---

Quick Start
Run the following command：

Clone project

git clone --depth 1 https://github.com/nacos-group/nacos-docker.git
cd nacos-docker
Standalone Derby

docker-compose -f example/standalone-derby.yaml up
Standalone Mysql

docker-compose -f example/standalone-mysql.yaml up
Cluster

docker-compose -f example/cluster-hostname.yaml up 
Service registration

curl -X PUT 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
Service discovery

  curl -X GET 'http://127.0.0.1:8848/nacos/v1/ns/instances?serviceName=nacos.naming.serviceName'
Publish config

curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test&content=helloWorld"
Get config

  curl -X GET "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test"
Open the Nacos console in your browser

link：http://127.0.0.1:8848/nacos/



--- 